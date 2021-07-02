package br.com.caelum.carangobom.dashboard.controllers;

import br.com.caelum.carangobom.dashboard.dtos.CarPriceByBrandItemResponse;
import br.com.caelum.carangobom.dashboard.services.DashboardService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class DashboardControllerTest {
	

	private DashboardController dashboardController;
	
	@Mock
	private DashboardService dashboardService;

	
	@BeforeEach
	public void setUp() {
		openMocks(this);
		
		this.dashboardController = new DashboardController(dashboardService);
	}
	
	@Test
	void shouldReturnCarsByBrand() {
		
		List<CarPriceByBrandItemResponse> totalPriceCarsByBrand = List.of(
				new CarPriceByBrandItemResponse("Fiat", BigDecimal.TEN, 10L),
				new CarPriceByBrandItemResponse("Ford", BigDecimal.ONE, 5L),
				new CarPriceByBrandItemResponse("Audi", BigDecimal.TEN, 3L)
		);
		
		
		when(dashboardService.totalPriceCarsByBrand())
			.thenReturn(totalPriceCarsByBrand);
		
		ResponseEntity<List<CarPriceByBrandItemResponse>> result 
			= dashboardController.totalPriceCarsByBrand();
		
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(totalPriceCarsByBrand, result.getBody());
	}
	

}
