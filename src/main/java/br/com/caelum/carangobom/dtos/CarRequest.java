package br.com.caelum.carangobom.dtos;

import java.math.BigDecimal;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;
import lombok.Value;

@Value
public class CarRequest {

	@NotNull
	Long idBrand;
	
	@NotBlank
	String model;
	
	@NotNull
	/*
	 * TODO: Criar anotação para validar ano menor que atual mais um
	 */
	Integer year;
	
	@NotNull
	BigDecimal value;

	public Car toModel() {
		final Brand brand = new Brand(this.getIdBrand());

		return new Car(brand, this.getModel(), this.getYear(), this.getValue());
	}

}
