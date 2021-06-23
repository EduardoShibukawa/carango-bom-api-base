package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.dtos.CarFilterRequest;
import br.com.caelum.carangobom.dtos.CarRequest;
import br.com.caelum.carangobom.exceptions.CarNotFoundException;
import br.com.caelum.carangobom.services.CarService;


@RestController
@RequestMapping("cars")
public class CarController {
	
	private final CarService carService;
	
	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping	
	public List<CarDetailResponse> getAll(CarFilterRequest filter) {
		return carService.findAll(filter);
	}

	@PostMapping		
	public ResponseEntity<CarDetailResponse> save(@RequestBody @Valid CarRequest carRequest, UriComponentsBuilder uriBuilder) {
		CarDetailResponse carDetailResponse = carService.save(carRequest);
		
		URI uri = uriBuilder
    			.path("/cars/{id}")
    			.buildAndExpand(carDetailResponse.getId())
    			.toUri();
		
		return ResponseEntity.created(uri).body(carDetailResponse);
		
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable Long id) {
		try {
			carService.delete(id);
			return ResponseEntity.ok().build();
		}catch (CarNotFoundException e){
			return ResponseEntity.notFound().build();
		}
	}

	@PutMapping("/{id}")
	public ResponseEntity<CarDetailResponse> update(@PathVariable long id, @RequestBody @Valid CarRequest carRequest) {
		try {
			CarDetailResponse response = carService.update(id, carRequest);
			return ResponseEntity.ok(response);
		}catch (CarNotFoundException e){
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/{id}")
	public ResponseEntity<CarDetailResponse> findById(@PathVariable long id) {
		try {
			return ResponseEntity.ok(carService.findById(id));
		}catch (CarNotFoundException e){
			return ResponseEntity.notFound().build();
		}
	}
}
