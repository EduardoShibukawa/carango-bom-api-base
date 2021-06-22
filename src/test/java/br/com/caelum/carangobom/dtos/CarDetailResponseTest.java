package br.com.caelum.carangobom.dtos;

import static org.junit.jupiter.api.Assertions.*;

import java.math.BigDecimal;

import org.junit.jupiter.api.Test;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;

class CarDetailResponseTest {

	@Test
	void createFromModel() {
		Car car = new Car(2L, new Brand("Ford"),"Ka", 2011, BigDecimal.valueOf(10000L));
		
		CarDetailResponse result = CarDetailResponse.fromModel(car);
		
		assertEquals(car.getId(), result.getId());
		assertEquals(car.getBrand().getName(), result.getBrand());
		assertEquals(car.getModel(), result.getModel());
		assertEquals(car.getYear(), result.getYear());
		assertEquals(car.getValue(), result.getValue());
	}

}
