package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.Car;

import java.math.BigDecimal;
import java.util.Objects;

public class CarDetailResponse {

	private Long id;
	private BrandResponse brand;
	private String model;
	private Integer year;
	private BigDecimal value;
	
	public CarDetailResponse() {}

	public CarDetailResponse(Long id, BrandResponse brand, String model, Integer year, BigDecimal value) {
		super();
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.value = value;
	}
	
	public static CarDetailResponse fromModel(Car car) {
		return new CarDetailResponse(
				car.getId(),
				BrandResponse.fromModel(Objects.requireNonNull(car.getBrand(), "Brand is empty")),
				car.getModel(), 
				car.getYear(), 
				car.getValue());
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public BrandResponse getBrand() {
		return brand;
	}

	public void setBrand(BrandResponse brand) {
		this.brand = brand;
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
}
