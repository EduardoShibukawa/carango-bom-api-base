package br.com.caelum.carangobom.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.dtos.CarRequest;
import br.com.caelum.carangobom.repositories.CarRepository;

@Service
public class CarService {

	private CarRepository carRepository;

	@Autowired
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	public List<CarDetailResponse> findAll() {
		return carRepository.findAll().stream().map(CarDetailResponse::fromModel)
				.collect(Collectors.toUnmodifiableList());
	}

	public CarDetailResponse save(CarRequest carRequest) {
		final Car car = carRequest.toModel();

		return CarDetailResponse.fromModel(carRepository.save(car));
	}

}
