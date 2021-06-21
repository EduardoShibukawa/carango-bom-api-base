package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.Brand;

import java.util.Objects;

public class BrandResponse {
    private Long id;
    private String name;

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public BrandResponse(Long id, String name) {
        this.id = id;
        this.name = name;
    }

    public static BrandResponse fromModel(Brand brand){
        return new BrandResponse(brand.getId(), brand.getName());
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        BrandResponse that = (BrandResponse) o;
        return Objects.equals(id, that.id) && Objects.equals(name, that.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name);
    }
}