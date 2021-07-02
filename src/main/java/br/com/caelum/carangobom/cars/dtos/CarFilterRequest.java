package br.com.caelum.carangobom.cars.dtos;

import lombok.RequiredArgsConstructor;
import lombok.Value;

import java.math.BigDecimal;

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
