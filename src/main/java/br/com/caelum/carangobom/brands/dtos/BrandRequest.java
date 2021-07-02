package br.com.caelum.carangobom.brands.dtos;

import br.com.caelum.carangobom.brands.entities.Brand;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class BrandRequest {
	
    @NotBlank
    @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
    String name;

    public Brand toModel(){
        return new Brand(this.name);
    }
    
}
