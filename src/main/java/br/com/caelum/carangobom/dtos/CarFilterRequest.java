package br.com.caelum.carangobom.dtos;

import java.math.BigDecimal;

public class CarFilterRequest {

	private String model;
	
	private Long idBrand;
	
	private BigDecimal minValue;
	
	private BigDecimal maxValue;

	public CarFilterRequest() {
	}
	
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Long getIdBrand() {
		return idBrand;
	}
	
	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}

	public BigDecimal getMinValue() {
		return minValue;
	}

	public void setMinValue(BigDecimal minValue) {
		this.minValue = minValue;
	}

	public BigDecimal getMaxValue() {
		return maxValue;
	}

	public void setMaxValue(BigDecimal maxValue) {
		this.maxValue = maxValue;
	}
}
