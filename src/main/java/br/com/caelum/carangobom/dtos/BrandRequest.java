package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.Brand;
import org.hibernate.annotations.common.reflection.XProperty;

public class BrandRequest {
    private String name;

    public BrandRequest() {
    }

    public BrandRequest(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Brand toModel(){
        return new Brand(this.name);
    }
}
