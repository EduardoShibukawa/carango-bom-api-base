package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.Brand;
import lombok.Value;


@Value
public class BrandResponse {
    
	Long id;
	
    String name;
    
    public static BrandResponse fromModel(Brand brand){
        return new BrandResponse(brand.getId(), brand.getName());
    }
}