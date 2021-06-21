package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.dtos.BrandRequest;
import br.com.caelum.carangobom.dtos.BrandResponse;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.repositories.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class BrandServiceTest {

    private  BrandService brandService;
    @Mock
    private BrandRepository brandRepository;

    @BeforeEach
    public void configureMock() {
        openMocks(this);
        brandService = new BrandService(brandRepository);
    }

    @Test
    void finById_success() {
        Brand brand = new Brand(1L, "Audi");
        BrandResponse brandResponse = new BrandResponse(1L, "Audi");

        when(brandRepository.findBrand(1L)).thenReturn(brand);

        BrandResponse result = brandService.findById(1L);
        assertEquals(brandResponse, result);
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

        brandService.save(brandRequest);

        verify(brandRepository).save(any());
    }

    @Test
    void delete_success() {
        Brand brand = new Brand("Audi");

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

        assertEquals(brands, brandService.findAllByOrderByName());
    }

    @Test
    void findAllByOrderByName_shouldReturnNotEmpty() {
        List<Brand> brands = List.of(new Brand(1L, "Audi"), new Brand(2L, "Ford"));
        when(brandRepository.findAllByOrderByName()).thenReturn(brands);

        assertEquals(brands, brandService.findAllByOrderByName());
    }

    @Test
    void whenUpdate_notFoundBrand_shouldThrowBrandNotFoundException() {
        doThrow(BrandNotFoundException.class)
                .when(brandRepository)
                .findBrand(1L);

        assertThrows(BrandNotFoundException.class, () -> brandService.update(1L, new BrandRequest("Audi")));
    }

    @Test
    void whenUpdate_foundBrand_shouldUpdate() {
        BrandRequest brandRequest = new BrandRequest("New Audi");
        Brand brand = new Brand("Audi");
        when(brandRepository.findBrand(1L)).thenReturn(brand);

        brandService.update(1L, brandRequest);

        verify(brandRepository).save(brand);
    }
}