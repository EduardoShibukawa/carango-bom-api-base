package br.com.caelum.carangobom.dtos;

import java.math.BigDecimal;

import lombok.RequiredArgsConstructor;
import lombok.Value;

@Value
@RequiredArgsConstructor
public class CarFilterRequest {

	String model;
	
	Long idBrand;
	
	BigDecimal minValue;
	
	BigDecimal maxValue;
	
	public CarFilterRequest() {
		this.model = "";
		this.idBrand = 0L;
		this.minValue = null;
		this.maxValue = null;
	}
	
}
