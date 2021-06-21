package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;

class BrandResponseTest {
    @Test
    void createBrandResponse() {
        Brand brand = new Brand(1L, "Audi");
        BrandResponse brandResponse = BrandResponse.fromModel(brand);

        assertEquals(brand.getName(), brandResponse.getName());
        assertEquals(brand.getId(), brandResponse.getId());
    }
}