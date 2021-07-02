package br.com.caelum.carangobom.cars.entities;

import br.com.caelum.carangobom.brands.entities.Brand;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
