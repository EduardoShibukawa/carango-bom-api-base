package br.com.caelum.carangobom.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.services.CarService;

public class CarController {
	
	private final CarService carService;
	
	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	public List<CarDetailResponse> getAll() {
		return carService.findAll();
	}

}
