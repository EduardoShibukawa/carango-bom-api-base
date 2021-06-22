package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.dtos.CarRequest;
import br.com.caelum.carangobom.exceptions.CarNotFoundException;
import br.com.caelum.carangobom.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class CarServiceTest {

	private CarService carService;
	
	@Mock
	private CarRepository carRepositoryMock;
	
	@BeforeEach
	public void setUp() {
        openMocks(this);
		this.carService = new CarService(carRepositoryMock);
	}
	
	@Test
	void findAll_shouldReturnCarDetails() {
        List<Car> cars = List.of(
    		new Car(1L, new Brand("Audi"), "A3", 2016, BigDecimal.valueOf(150000L)), 
    		new Car(2L, new Brand("Ford"),"Ka", 2011, BigDecimal.valueOf(10000L)), 
    		new Car(3L, new Brand("Fiat"), "Uno", 2000, BigDecimal.valueOf(5000L))
		);
        
        when(carRepositoryMock.findAll()).thenReturn(cars);
        
        List<CarDetailResponse> result = this.carService.findAll();
        
        assertThat(result, hasSize(3));
        assertThat(result , contains(
        		allOf(
    				hasProperty("id", is(1L)), 
    				hasProperty("brand", is("Audi")),
    				hasProperty("model", is("A3")),
					hasProperty("year", is(2016)),
					hasProperty("value", is(BigDecimal.valueOf(150000L)))
						
				),
        		allOf(
    				hasProperty("id", is(2L)), 
    				hasProperty("brand", is("Ford")),
    				hasProperty("model", is("Ka")),
					hasProperty("year", is(2011)),
					hasProperty("value", is(BigDecimal.valueOf(10000L)))
						
				),
        		allOf(
    				hasProperty("id", is(3L)), 
    				hasProperty("brand", is("Fiat")),
    				hasProperty("model", is("Uno")),
					hasProperty("year", is(2000)),
					hasProperty("value", is(BigDecimal.valueOf(5000L)))	
				)
		));
	}
	
	@Test
	void save_shouldCreate() {
		CarRequest carRequest = new CarRequest(1l, "KA", 2011, BigDecimal.valueOf(10000L));
		Car car = new Car(1l, new Brand(1l, "Ford"), "KA", 2011, BigDecimal.valueOf(10000L));
		
		
		when(this.carRepositoryMock.save(any(Car.class)))
			.thenReturn(car);
		
		CarDetailResponse result = this.carService.save(carRequest);
		
		assertEquals(car.getId(), result.getId());
		assertEquals(car.getBrand().getName(), result.getBrand());
		assertEquals(car.getModel(), result.getModel());
		assertEquals(car.getYear(), result.getYear());
		assertEquals(car.getValue(), result.getValue());
		
		verify(carRepositoryMock).save(any(Car.class));
		
	}

	@Test
	void should_delete() {
		Car car = new Car();
		when(carRepositoryMock.findCar(1L))
				.thenReturn(car);

		carService.delete(1L);

		verify(carRepositoryMock).delete(car);
	}

	@Test
	void whenDeleteAndCarNotExists_shouldThrowCarNotFoundException() {
		doThrow(CarNotFoundException.class)
				.when(carRepositoryMock)
				.findCar(1L);

		assertThrows(CarNotFoundException.class, () -> carService.delete(1L));
	}
}
