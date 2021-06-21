package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.Brand;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class BrandRequestTest {
    @Test
    void createBrandFromModel() {
        BrandRequest brandRequest = new BrandRequest("Audi");
        Brand brand = brandRequest.toModel();

        assertEquals(brandRequest.getName(), brand.getName());
    }
}