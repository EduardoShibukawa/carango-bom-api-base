package br.com.caelum.carangobom.brands.dtos;

import br.com.caelum.carangobom.brands.entities.Brand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BrandRequestTest {
    @Test
    void createBrandFromRequest() {
        BrandRequest brandRequest = new BrandRequest("Audi");
        Brand brand = brandRequest.toModel();

        assertEquals(brandRequest.getName(), brand.getName());
    }
}