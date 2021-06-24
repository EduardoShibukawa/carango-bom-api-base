package br.com.caelum.carangobom.domain;

import java.math.BigDecimal;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

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
	
	public Car(Brand brand, String model, Integer year, BigDecimal value) {
		this.brand = brand;
		this.model = model;
		this.year = year;
		this.value = value;
	}
}
