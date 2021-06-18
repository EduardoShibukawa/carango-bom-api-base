package br.com.caelum.carangobom.marca;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controllers.BrandController;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.services.BrandService;

class BrandControllerTest {

	private BrandController brandController;
	private UriComponentsBuilder uriBuilder;

	@Mock
	private BrandService brandService;

	@BeforeEach
	public void configuraMock() {
		openMocks(this);

		brandController = new BrandController(brandService);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void deveRetornarListaQuandoHouverResultados() {
		List<Brand> marcas = List.of(new Brand(1L, "Audi"), new Brand(2L, "BMW"), new Brand(3L, "Fiat"));

		when(brandService.findAllByOrderByNome()).thenReturn(marcas);

		List<Brand> resultado = brandController.getAll();
		assertEquals(marcas, resultado);
	}

	@Test
	void deveRetornarMarcaPeloId() {
		Brand audi = new Brand(1L, "Audi");

		when(brandService.findById(1L)).thenReturn(audi);

		ResponseEntity<Brand> resposta = brandController.findById(1L);

		assertEquals(audi, resposta.getBody());
		assertEquals(HttpStatus.OK, resposta.getStatusCode());
	}

	@Test
	void deveRetornarNotFoundQuandoRecuperarMarcaComIdInexistente() {
		doThrow(BrandNotFoundException.class).when(brandService).findById(anyLong());

		ResponseEntity<Brand> resposta = brandController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	void deveResponderCreatedELocationQuandoCadastrarMarca() {
		Brand nova = new Brand("Ferrari");

		when(brandService.save(nova)).then(invocation -> {
			Brand brandSalva = invocation.getArgument(0, Brand.class);
			brandSalva.setId(1L);

			return brandSalva;
		});

		ResponseEntity<Brand> resposta = brandController.save(nova, uriBuilder);
		assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
		assertEquals("http://localhost:8080/brands/1", resposta.getHeaders().getLocation().toString());
	}

	@Test
	void shouldUpdateNameWhenBrandExists() {
		Brand brandRequest = new Brand(1L, "NOVA Audi");

		when(brandService.update(1L, brandRequest))
			.thenReturn(brandRequest);

		ResponseEntity<Brand> response = brandController.update(1L, brandRequest);
		Brand brandResponse = response.getBody();
		
		assertEquals(HttpStatus.OK, response.getStatusCode());		
		assertEquals("NOVA Audi", brandResponse.getNome());
	}

	@Test
	void shouldReturnNotFoundIfBrandNotExits() {
		doThrow(BrandNotFoundException.class)
			.when(brandService)
			.update(anyLong(), any());

		ResponseEntity<Brand> resposta = brandController.update(1L, new Brand(1L, "NOVA Audi"));

		assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
	}

	@Test
	void shoudlDeleteBrand() {
		Brand brand = new Brand(1l, "Audi");

		when(brandService.findById(1L))
			.thenReturn(brand);

		ResponseEntity<Brand> response = brandController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(brandService).delete(1L);
	}

	@Test
	void shouldReturnNotFoundIfNotExists() {
		doThrow(BrandNotFoundException.class)
			.when(brandService)
			.delete(anyLong());

		ResponseEntity<Brand> response = brandController.delete(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		verify(brandService, times(1)).delete(any());
	}

}