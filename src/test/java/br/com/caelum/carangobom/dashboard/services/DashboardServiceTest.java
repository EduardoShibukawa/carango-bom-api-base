package br.com.caelum.carangobom.dashboard.services;

import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.cars.entities.Car;
import br.com.caelum.carangobom.cars.repositories.CarRepository;
import br.com.caelum.carangobom.dashboard.dtos.CarPriceByBrandItemResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.math.BigDecimal;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DashboardServiceTest {

	private DashboardService dashboardService;

	@Mock
	private CarRepository carRepositoryMock;
	
	@BeforeEach
	public void setUp() {
        openMocks(this);
        
		this.dashboardService = new DashboardService(carRepositoryMock);
	}
	
	@Test
	void findAll_shouldReturnEmptyArray() {
        List<Car> cars = List.of();
        
		when(carRepositoryMock.findAll())
			.thenReturn(cars);
		
		List<CarPriceByBrandItemResponse> result = dashboardService.totalPriceCarsByBrand();

        assertThat(result, hasSize(0));	
    }

	
	@Test
	void findAll_shouldReturnCarByBrand() {
        List<Car> cars = List.of(
    		new Car(1L, new Brand(1L, "Audi"), "A3", 2016, BigDecimal.valueOf(150000L)),
    		new Car(3L, new Brand(3L, "Fiat"), "Uno", 2000, BigDecimal.valueOf(5000L)),
    		new Car(2L, new Brand(2L, "Ford"), "Ka", 2011, BigDecimal.valueOf(10000L)),
    		new Car(4L, new Brand(3L, "Fiat"), "Uno", 2000, BigDecimal.valueOf(5500L)),
    		new Car(5L, new Brand(3L, "Fiat"), "Uno", 2000, BigDecimal.valueOf(5500L))
		);
        
		when(carRepositoryMock.findAll())
			.thenReturn(cars);
		
		List<CarPriceByBrandItemResponse> result = dashboardService.totalPriceCarsByBrand();

        assertThat(result, hasSize(3));
        assertThat(result, contains(
        		allOf(
    				hasProperty("brand", is("Fiat")), 
    				hasProperty("count", is(3L)),
    				hasProperty("totalPrice", is(BigDecimal.valueOf(16000)))
				),
        		allOf(
    				hasProperty("brand", is("Audi")), 
    				hasProperty("count", is(1L)),
    				hasProperty("totalPrice", is(BigDecimal.valueOf(150000)))
        		),
        		allOf(
    				hasProperty("brand", is("Ford")), 
    				hasProperty("count", is(1L)),
    				hasProperty("totalPrice", is(BigDecimal.valueOf(10000)))
				)
		));
	}
}
