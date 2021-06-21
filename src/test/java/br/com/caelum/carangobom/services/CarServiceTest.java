package br.com.caelum.carangobom.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigDecimal;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.repositories.CarRepository;

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

}
