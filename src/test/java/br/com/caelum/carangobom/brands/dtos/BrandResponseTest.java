package br.com.caelum.carangobom.brands.dtos;

import br.com.caelum.carangobom.brands.entities.Brand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandResponseTest {
    @Test
    void createBrandResponse() {
        Brand brand = new Brand(1L, "Audi");
        BrandResponse brandResponse = BrandResponse.fromModel(brand);

        assertEquals(brand.getName(), brandResponse.getName());
        assertEquals(brand.getId(), brandResponse.getId());
    }
}