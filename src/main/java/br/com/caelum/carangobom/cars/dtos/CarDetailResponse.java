package br.com.caelum.carangobom.cars.dtos;

import br.com.caelum.carangobom.brands.dtos.BrandResponse;
import br.com.caelum.carangobom.cars.entities.Car;
import lombok.Value;

import java.math.BigDecimal;
import java.util.Objects;

@Value
public class CarDetailResponse {

	Long id;
	
	BrandResponse brand;
	
	String model;
	
	Integer year;
	
	BigDecimal value;
	
	public static CarDetailResponse fromModel(Car car) {
		return new CarDetailResponse(
				car.getId(),
				BrandResponse.fromModel(Objects.requireNonNull(car.getBrand(), "Brand is empty")),
				car.getModel(), 
				car.getYear(), 
				car.getValue());
	}
}
