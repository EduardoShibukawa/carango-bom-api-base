package br.com.caelum.carangobom.controllers;

import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.dtos.CarRequest;
import br.com.caelum.carangobom.services.CarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("cars")
public class CarController {
	
	private final CarService carService;
	
	@Autowired
	public CarController(CarService carService) {
		this.carService = carService;
	}

	@GetMapping	
	public List<CarDetailResponse> getAll() {
		return carService.findAll();
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
	public ResponseEntity<?> delete(@PathVariable Long id) {
		carService.delete(id);
		return ResponseEntity.ok().build();
	}

	@PutMapping("/{id}")
	public ResponseEntity<CarDetailResponse> update(@PathVariable long id, @RequestBody @Valid CarRequest carRequest) {
		CarDetailResponse response = carService.update(id, carRequest);
		return ResponseEntity.ok(response);
	}
}
