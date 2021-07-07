package br.com.caelum.carangobom.brands.services;

import br.com.caelum.carangobom.brands.dtos.BrandRequest;
import br.com.caelum.carangobom.brands.dtos.BrandResponse;
import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.brands.exceptions.BrandAlreadyExistException;
import br.com.caelum.carangobom.brands.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.brands.repositories.BrandRepository;
import br.com.caelum.carangobom.cars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandServiceTest {

    private BrandService brandService;
    @Mock
    private BrandRepository brandRepository;
    @Mock
    private CarRepository carRepository;

    @BeforeEach
    public void configureMock() {
        openMocks(this);
        brandService = new BrandService(brandRepository, carRepository);
    }

    @Test
    void finById_success() {
        Brand brand = new Brand(1L, "Audi");
        BrandResponse brandResponse = new BrandResponse(1L, "Audi");

        when(brandRepository.findBrand(1L)).thenReturn(brand);

        BrandResponse result = brandService.findById(1L);
        
        assertEquals(brandResponse, result);
        assertEquals(brandResponse.hashCode(), result.hashCode());
    }

    @Test
    void findBrandById_shouldThrowBrandNotFoundException() {
        doThrow(BrandNotFoundException.class)
                .when(brandRepository)
                .findBrand(1L);

        assertThrows(BrandNotFoundException.class, () -> brandService.findById(1L));
    }

    @Test
    void save_success() {
        BrandRequest brandRequest = new BrandRequest("Audi");
        
        when(brandRepository.save(any()))
        	.thenReturn(brandRequest.toModel());

        brandService.save(brandRequest);

        verify(brandRepository).save(any());
    }

    @Test
    void whenSaveAndBrandAlreadyExists_ShouldThrowBrandAlreadyExistsException(){
        BrandRequest brandRequest = new BrandRequest("Audi");
        Optional<Brand> optionalBrand = Optional.of(new Brand("A Brand"));

        when(brandRepository.findByName(any())).thenReturn(optionalBrand);

        assertThrows(BrandAlreadyExistException.class, () -> brandService.save(brandRequest));
    }

    @Test
    void delete_success() {
        Brand brand = new Brand(1L, "Audi");

        when(brandRepository.findBrand(1L)).thenReturn(brand);

        brandService.delete(1L);

        verify(brandRepository).delete(brand);
    }

    @Test
    void delete_shouldThrowBrandNotFoundException() {
        doThrow(BrandNotFoundException.class)
                .when(brandRepository)
                .findBrand(1L);

        assertThrows(BrandNotFoundException.class, () -> brandService.delete(1L));
    }

    @Test
    void findAllByOrderByName_shouldReturnEmpty() {
        List<Brand> brands = List.of();
        when(brandRepository.findAllByOrderByName()).thenReturn(brands);

        assertThat(brandService.findAllByOrderByName(), empty());
    }

    @Test
    void findAllByOrderByName_shouldReturnNotEmpty() {
        List<Brand> brands = List.of(new Brand(1L, "Audi"), new Brand(2L, "Ford"));
        
        when(brandRepository.findAllByOrderByName()).thenReturn(brands);
        
        List<BrandResponse> brandsResponse = brandService.findAllByOrderByName();
        
        assertThat(brandsResponse, hasSize(2));
        assertThat(brandsResponse , contains(
        		allOf(hasProperty("id", is(1L)), 
        				hasProperty("name", is("Audi"))),
        		allOf(hasProperty("id", is(2L)), 
        				hasProperty("name", is("Ford")))
		));
    }

    @Test
    void whenUpdate_notFoundBrand_shouldThrowBrandNotFoundException() {
    	final BrandRequest brandRequest = new BrandRequest("Audi");
        doThrow(BrandNotFoundException.class)
                .when(brandRepository)
                .findBrand(1L);

        assertThrows(BrandNotFoundException.class, () -> {
			brandService.update(1L, brandRequest);
		});
    }

    @Test
    void whenUpdate_foundBrand_shouldUpdate() {
        BrandRequest brandRequest = new BrandRequest("New Audi");
        Brand brand = new Brand(1L, "Audi");
        
        when(brandRepository.findBrand(1L))
        	.thenReturn(brand);
        when(brandRepository.save(brand))
        	.thenReturn(brand);

        brandService.update(1L, brandRequest);

        verify(brandRepository).save(brand);
    }
}
