package br.com.caelum.carangobom.dtos;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import br.com.caelum.carangobom.domain.Brand;

class BrandResponseTest {
    @Test
    void createBrandResponse() {
        Brand brand = new Brand(1L, "Audi");
        BrandResponse brandResponse = BrandResponse.fromModel(brand);

        assertEquals(brand.getName(), brandResponse.getName());
        assertEquals(brand.getId(), brandResponse.getId());
    }
}