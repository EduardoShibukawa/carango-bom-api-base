package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.repositories.BrandRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
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
    void findBrandById_success() {
        Optional<Brand> brand = Optional.of(new Brand(1L, "Audi"));

        when(brandRepository.findById(1L)).thenReturn(brand);

        Brand result = brandService.findById(1L);
        assertEquals(brand.get(), result);
    }

    @Test
    void findBrandById_shouldThrowBrandNotFoundException() {
        Optional<Brand> brand = Optional.empty();

        when(brandRepository.findById(1L)).thenReturn(brand);

        assertThrows(BrandNotFoundException.class, () -> brandService.findById(1L));
    }

    @Test
    void save_success() {
        Brand brand = new Brand("Audi");

        brandService.save(brand);

        verify(brandRepository).save(brand);
    }

    @Test
    void delete_success() {
        Brand brand = new Brand("Audi");

        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        brandService.delete(1L);

        verify(brandRepository).delete(brand);
    }

    @Test
    void delete_shouldThrowBrandNotFoundException() {
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

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
        when(brandRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(BrandNotFoundException.class, () -> brandService.update(1L, new Brand("Audi")));
    }

    @Test
    void whenUpdate_foundBrand_shouldUpdate() {

        Brand brandRequest = new Brand("New Audi");
        Brand brand = new Brand("Audi");
        when(brandRepository.findById(1L)).thenReturn(Optional.of(brand));

        brandService.update(1L, brandRequest);

        verify(brandRepository).save(brand);
    }
}