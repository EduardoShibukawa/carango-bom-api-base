package br.com.caelum.carangobom.marca;

import br.com.caelum.carangobom.controllers.BrandController;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.repositories.BrandRepository;
import br.com.caelum.carangobom.services.BrandService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

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

//    @Test
//    void deveRetornarListaQuandoHouverResultados() {
//        List<Marca> marcas = List.of(
//            new Marca(1L, "Audi"),
//            new Marca(2L, "BMW"),
//            new Marca(3L, "Fiat")
//        );
//
//        when(marcaRepository.findAllByOrderByNome())
//            .thenReturn(marcas);
//
//        List<Marca> resultado = marcaController.lista();
//        assertEquals(marcas, resultado);
//    }

    @Test
    void deveRetornarMarcaPeloId() {
        Brand audi = new Brand(1L, "Audi");

        when(brandService.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Brand> resposta = brandController.findById(1L);
        assertEquals(audi, resposta.getBody());
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
    }

    @Test
    void deveRetornarNotFoundQuandoRecuperarMarcaComIdInexistente() {
        when(brandService.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Brand> resposta = brandController.findById(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveResponderCreatedELocationQuandoCadastrarMarca() {
        Brand nova = new Brand("Ferrari");

        when(brandService.save(nova))
            .then(invocation -> {
                Brand brandSalva = invocation.getArgument(0, Brand.class);
                brandSalva.setId(1L);

                return brandSalva;
            });

        ResponseEntity<Brand> resposta = brandController.save(nova, uriBuilder);
        assertEquals(HttpStatus.CREATED, resposta.getStatusCode());
        assertEquals("http://localhost:8080/brands/1", resposta.getHeaders().getLocation().toString());
    }

    @Test
    void deveAlterarNomeQuandoMarcaExistir() {
        Brand audi = new Brand(1L, "Audi");

        when(brandService.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Brand> resposta = brandController.update(1L, new Brand(1L, "NOVA Audi"));
        assertEquals(HttpStatus.OK, resposta.getStatusCode());

        Brand brandAlterada = resposta.getBody();
        assertEquals("NOVA Audi", brandAlterada.getNome());
    }

    @Test
    void naoDeveAlterarMarcaInexistente() {
        when(brandService.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Brand> resposta = brandController.update(1L, new Brand(1L, "NOVA Audi"));
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());
    }

    @Test
    void deveDeletarMarcaExistente() {
        Brand audi = new Brand(1l, "Audi");

        when(brandService.findById(1L))
            .thenReturn(Optional.of(audi));

        ResponseEntity<Brand> resposta = brandController.delete(1L);
        assertEquals(HttpStatus.OK, resposta.getStatusCode());
        verify(brandService).delete(audi);
    }

    @Test
    void naoDeveDeletarMarcaInexistente() {
        when(brandService.findById(anyLong()))
                .thenReturn(Optional.empty());

        ResponseEntity<Brand> resposta = brandController.delete(1L);
        assertEquals(HttpStatus.NOT_FOUND, resposta.getStatusCode());

        verify(brandService, never()).delete(any());
    }

}