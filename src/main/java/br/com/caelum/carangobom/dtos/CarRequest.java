package br.com.caelum.carangobom.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;

public class CarRequest {

	@NotNull
	private Long idBrand;
	
	@NotBlank
	private String model;
	
	@NotNull
	/*
	 * TODO: Criar anotação para validar ano menor que atual mais um
	 */
	private Integer year;
	
	@NotNull
	private BigDecimal value;

	public CarRequest(Long idBrand, String model, Integer year, BigDecimal value) {
		this.idBrand = idBrand;
		this.model = model;
		this.year = year;
		this.value = value;
	}

	public Long getIdBrand() {
		return idBrand;
	}

	public void setIdBrand(Long idBrand) {
		this.idBrand = idBrand;
	}

	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	public Integer getYear() {
		return year;
	}

	public void setYear(Integer year) {
		this.year = year;
	}

	public BigDecimal getValue() {
		return value;
	}

	public void setValue(BigDecimal value) {
		this.value = value;
	}

	public Car toModel() {
		final Brand brand = new Brand(this.getIdBrand());

		return new Car(brand, this.getModel(), this.getYear(), this.getValue());
	}

}
