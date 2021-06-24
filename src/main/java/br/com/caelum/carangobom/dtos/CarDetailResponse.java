package br.com.caelum.carangobom.dtos;

import java.math.BigDecimal;
import java.util.Objects;

import br.com.caelum.carangobom.domain.Car;
import lombok.Value;

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
