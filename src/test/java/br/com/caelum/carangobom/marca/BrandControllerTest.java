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

import br.com.caelum.carangobom.dtos.BrandRequest;
import br.com.caelum.carangobom.dtos.BrandResponse;
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
	public void configureMock() {
		openMocks(this);

		brandController = new BrandController(brandService);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void shouldReturnBrandsWhenExists() {
		List<BrandResponse> brandsResponses = List.of(
				new BrandResponse(1L, "Audi"), 
				new BrandResponse(2L, "BMW"), 
				new BrandResponse(3L, "Fiat"));

		when(brandService.findAllByOrderByName())
			.thenReturn(brandsResponses);

		List<BrandResponse> response = brandController.getAll();
		
		assertEquals(brandsResponses, response);
	}

	@Test
	void shouldReturnBrandById() {
		BrandResponse brandResponse = new BrandResponse(1L, "Audi");

		when(brandService.findById(1L)).thenReturn(brandResponse);

		ResponseEntity<BrandResponse> response = brandController.findById(1L);

		assertEquals(brandResponse, response.getBody());
		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	void shouldReturnNotFoundWhenBrandNotExists_findById() {
		doThrow(BrandNotFoundException.class)
			.when(brandService)
			.findById(anyLong());

		ResponseEntity<BrandResponse> result = brandController.findById(1L);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	void shouldReturnCreatedWithLocationWhenBrandIsCreated(){
		BrandRequest brandRequest = new BrandRequest("Ferrari");

		when(brandService.save(brandRequest)).then(invocation -> {
			BrandRequest brandRequestArgument = invocation.getArgument(0, BrandRequest.class);
			Brand brandSaved = brandRequestArgument.toModel();
			
			brandSaved.setId(1L);

			return BrandResponse.fromModel(brandSaved);
		});

		ResponseEntity<BrandResponse> result = brandController.save(brandRequest, uriBuilder);
		
		assertEquals(HttpStatus.CREATED, result.getStatusCode());
		assertEquals("http://localhost:8080/brands/1", result.getHeaders().getLocation().toString());
	}

	@Test
	void shouldUpdateNameWhenBrandExists() {
		BrandRequest brandRequest = new BrandRequest( "NOVA Audi");

		when(brandService.update(1L, brandRequest))
			.thenReturn(new BrandResponse(1L, "NOVA Audi"));

		ResponseEntity<BrandResponse> response = brandController.update(1L, brandRequest);
		BrandResponse brandResponse = response.getBody();

		assertEquals(HttpStatus.OK, response.getStatusCode());		
		assertEquals("NOVA Audi", brandResponse.getName());
	}

	@Test
	void shouldReturnNotFoundIfBrandNotExits_update() {
		doThrow(BrandNotFoundException.class)
			.when(brandService)
			.update(anyLong(), any());

		ResponseEntity<BrandResponse> result = brandController.update(1L, new BrandRequest( "NOVA Audi"));

		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
	}

	@Test
	void shoudlDeleteBrand() {
		ResponseEntity<?> response = brandController.delete(1L);
		assertEquals(HttpStatus.OK, response.getStatusCode());

		verify(brandService).delete(1L);
	}

	@Test
	void shouldReturnNotFoundIfBrandNotExists_delete() {
		doThrow(BrandNotFoundException.class)
			.when(brandService)
			.delete(anyLong());

		ResponseEntity<?> response = brandController.delete(1L);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());

		verify(brandService, times(1)).delete(any());
	}
}