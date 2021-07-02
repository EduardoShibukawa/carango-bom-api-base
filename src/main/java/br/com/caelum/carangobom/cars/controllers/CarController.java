package br.com.caelum.carangobom.cars.controllers;

import br.com.caelum.carangobom.cars.dtos.CarDetailResponse;
import br.com.caelum.carangobom.cars.dtos.CarFilterRequest;
import br.com.caelum.carangobom.cars.dtos.CarRequest;
import br.com.caelum.carangobom.cars.exceptions.CarNotFoundException;
import br.com.caelum.carangobom.cars.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("cars")
@CrossOrigin(origins = "*", maxAge = 3600)
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
