package br.com.caelum.carangobom.cars.dtos;

import br.com.caelum.carangobom.cars.entities.Car;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CarRequestTest {

	@Test
	void createCarFromRequest() {
		CarRequest carRequest = new CarRequest(1L, "KA", 2011, BigDecimal.valueOf(10000L));
		
		Car result = carRequest.toModel();
		
		assertEquals(carRequest.getIdBrand(), result.getBrand().getId());
		assertEquals(carRequest.getModel(), result.getModel());
		assertEquals(carRequest.getYear(), result.getYear());
		assertEquals(carRequest.getValue(), result.getValue());
	}

}
