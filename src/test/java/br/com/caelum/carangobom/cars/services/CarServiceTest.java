package br.com.caelum.carangobom.cars.services;

import br.com.caelum.carangobom.brands.dtos.BrandResponse;
import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.cars.repositories.specification.CarFilterSpecification;
import br.com.caelum.carangobom.cars.dtos.CarDetailResponse;
import br.com.caelum.carangobom.cars.dtos.CarFilterRequest;
import br.com.caelum.carangobom.cars.dtos.CarRequest;
import br.com.caelum.carangobom.cars.entities.Car;
import br.com.caelum.carangobom.cars.exceptions.CarNotFoundException;
import br.com.caelum.carangobom.cars.repositories.CarRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
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
    		new Car(1L, new Brand(1L, "Audi"), "A3", 2016, BigDecimal.valueOf(150000L)),
    		new Car(2L, new Brand(2L, "Ford"),"Ka", 2011, BigDecimal.valueOf(10000L)),
    		new Car(3L, new Brand(3L, "Fiat"), "Uno", 2000, BigDecimal.valueOf(5000L))
		);
        
        when(carRepositoryMock.findAll(any(CarFilterSpecification.class)))
        	.thenReturn(cars);
        
        List<CarDetailResponse> result = this.carService.findAll(new CarFilterRequest());
        
        assertThat(result, hasSize(3));
        assertThat(result , contains(
        		allOf(
    				hasProperty("id", is(1L)), 
    				hasProperty("brand", is(new BrandResponse(1L, "Audi"))),
    				hasProperty("model", is("A3")),
					hasProperty("year", is(2016)),
					hasProperty("value", is(BigDecimal.valueOf(150000L)))
						
				),
        		allOf(
    				hasProperty("id", is(2L)), 
    				hasProperty("brand", is(new BrandResponse(2L,"Ford"))),
    				hasProperty("model", is("Ka")),
					hasProperty("year", is(2011)),
					hasProperty("value", is(BigDecimal.valueOf(10000L)))
						
				),
        		allOf(
    				hasProperty("id", is(3L)), 
    				hasProperty("brand", is(new BrandResponse(3L,"Fiat"))),
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
		assertEquals(car.getBrand().getName(), result.getBrand().getName());
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

	@Test
	void shouldUpdate() {
		CarRequest request = new CarRequest(1L, "New Ka", 2012, BigDecimal.valueOf(15000.00));

		Car carToUpdate = new Car(new Brand(1L, "Ford"), "Ka",2011,BigDecimal.valueOf(10000.00));
		Car carUpdated = new Car(new Brand(1L, "Ford"), "NewKa",2012,BigDecimal.valueOf(15000.00));

		when(carRepositoryMock.findCar(1L))
				.thenReturn(carToUpdate);
		when(carRepositoryMock.save(carToUpdate))
				.thenReturn(carUpdated);

		carService.update(1L, request);

		verify(carRepositoryMock).save(carToUpdate);
	}

	@Test
	void whenUpdateAndCarNotExists_shouldThrowCarNotFoundException() {
		CarRequest request = new CarRequest(1L, "New Ka", 2012, BigDecimal.valueOf(15000.00));

		doThrow(CarNotFoundException.class)
				.when(carRepositoryMock).findCar(1L);

		assertThrows(CarNotFoundException.class, () -> carService.update(1L, request));
	}

	@Test
	void whenFindCarById_shouldReturnCarDetailResponse() {
		Car car = new Car(new Brand(1L, "Ford"), "Ka", 2011, BigDecimal.valueOf(10000.00));
		when(carRepositoryMock.findCar(1L))
				.thenReturn(car);

		CarDetailResponse result = carService.findById(1L);

		assertEquals(car.getBrand().getName(), result.getBrand().getName());
		assertEquals(car.getBrand().getId(), result.getBrand().getId());
		assertEquals(car.getId(), result.getId());
		assertEquals(car.getModel(), result.getModel());
		assertEquals(car.getYear(), result.getYear());
		assertEquals(car.getValue(), result.getValue());
	}

	@Test
	void whenCarIsNotFound_shouldThrowCarNotFoundException() {
		doThrow(CarNotFoundException.class)
				.when(carRepositoryMock).findCar(1L);

		assertThrows(CarNotFoundException.class, () -> carService.findById(1L));
	}
}
