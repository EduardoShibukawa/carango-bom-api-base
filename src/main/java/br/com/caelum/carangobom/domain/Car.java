package br.com.caelum.carangobom.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

@Entity
public class Car {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@ManyToOne
	@JoinColumn(name = "BRAND_ID")
	private Brand brand;
	
	@NotNull
	private String model;
	
	@NotNull
	private Integer year;
	
	@NotNull
	private BigDecimal value;
	
	
	public Car() {}

	public Car(Brand brand, String model, Integer year, BigDecimal value) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.value = value;
	}


	public Car(Long id, Brand brand, String model, Integer year, BigDecimal value) {
		this.id = id;
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.value = value;
	}


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public Brand getBrand() {
		return brand;
	}


	public void setBrand(Brand brand) {
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
