package br.com.caelum.carangobom.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.domain.Brand;

public class BrandRequest {
	
    @NotBlank
    @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
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
