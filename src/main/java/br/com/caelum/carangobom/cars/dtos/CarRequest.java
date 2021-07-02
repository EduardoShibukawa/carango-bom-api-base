package br.com.caelum.carangobom.cars.dtos;

import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.cars.entities.Car;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

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
