package br.com.caelum.carangobom.cars.dtos;

import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.cars.entities.Car;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarDetailResponseTest {

	@Test
	void createFromModel() {
		Car car = new Car(2L, new Brand(1L, "Ford"),"Ka", 2011, BigDecimal.valueOf(10000L));
		
		CarDetailResponse result = CarDetailResponse.fromModel(car);
		
		assertEquals(car.getId(), result.getId());
		assertEquals(car.getBrand().getName(), result.getBrand().getName());
		assertEquals(car.getBrand().getId(), result.getBrand().getId());
		assertEquals(car.getModel(), result.getModel());
		assertEquals(car.getYear(), result.getYear());
		assertEquals(car.getValue(), result.getValue());
	}

}
