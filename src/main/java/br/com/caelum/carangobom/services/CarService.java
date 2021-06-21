package br.com.caelum.carangobom.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.repositories.CarRepository;


@Service
public class CarService {
	
	private CarRepository repository;
	
	@Autowired
	public CarService(CarRepository repository) {
		this.repository = repository;
	}

	public List<CarDetailResponse> findAll() {
		return repository
				.findAll()
				.stream()
				.map(CarDetailResponse::fromModel)
				.collect(Collectors.toUnmodifiableList());
	}

}
