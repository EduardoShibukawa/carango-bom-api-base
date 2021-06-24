package br.com.caelum.carangobom.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.domain.Brand;
import lombok.Value;

@Value
public class BrandRequest {
	
    @NotBlank
    @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
    String name;

    public Brand toModel(){
        return new Brand(this.name);
    }
    
}
