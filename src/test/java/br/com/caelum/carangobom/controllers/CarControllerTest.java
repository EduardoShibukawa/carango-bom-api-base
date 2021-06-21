package br.com.caelum.carangobom.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.math.BigDecimal;
import java.util.List;

import br.com.caelum.carangobom.dtos.BrandRequest;
import br.com.caelum.carangobom.dtos.BrandResponse;
import br.com.caelum.carangobom.dtos.CarDetailResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.controllers.BrandController;
import br.com.caelum.carangobom.controllers.CarController;
import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.exceptions.BrandNotFoundException;
import br.com.caelum.carangobom.services.BrandService;
import br.com.caelum.carangobom.services.CarService;

class CarControllerTest {

	private CarController carController;
	private UriComponentsBuilder uriBuilder;

	@Mock
	private CarService carService;

	@BeforeEach
	public void configureMock() {
		openMocks(this);

		carController = new CarController(carService);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void shouldReturnBrandsWhenExists() {
		List<CarDetailResponse> carDetailsResponse = List.of(
				new CarDetailResponse(1L, "Audi", "A3", 2016, BigDecimal.valueOf(150000L)), 
				new CarDetailResponse(2L, "Ford","Ka", 2011, BigDecimal.valueOf(10000L)), 
				new CarDetailResponse(3L, "Fiat", "Uno", 2000, BigDecimal.valueOf(5000L)));

		when(carService.findAll())
			.thenReturn(carDetailsResponse);

		List<CarDetailResponse> response = carController.getAll();
		
		assertEquals(carDetailsResponse, response);
	}
}