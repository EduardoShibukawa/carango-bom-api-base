package br.com.caelum.carangobom.cars.services;

import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.cars.dtos.CarDetailResponse;
import br.com.caelum.carangobom.cars.dtos.CarFilterRequest;
import br.com.caelum.carangobom.cars.dtos.CarRequest;
import br.com.caelum.carangobom.cars.entities.Car;
import br.com.caelum.carangobom.cars.repositories.CarRepository;
import br.com.caelum.carangobom.cars.repositories.specification.CarFilterSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;


@Service
@Transactional
public class CarService {

    private CarRepository carRepository;

    @Autowired
    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

	@Transactional(readOnly = true)
    public List<CarDetailResponse> findAll(CarFilterRequest filter) {
        return carRepository
        		.findAll(new CarFilterSpecification(filter))
        		.stream()
        		.map(CarDetailResponse::fromModel)
                .collect(Collectors.toUnmodifiableList());
    }

    public CarDetailResponse save(CarRequest carRequest) {
        final Car car = carRequest.toModel();

        return CarDetailResponse.fromModel(carRepository.save(car));
    }

    public void delete(Long id) {
        Car car = carRepository.findCar(id);
        carRepository.delete(car);
    }

    public CarDetailResponse update(long id, CarRequest request) {
        Car car = carRepository.findCar(id);

        car.setBrand(new Brand(request.getIdBrand()));
        car.setModel(request.getModel());
        car.setYear(request.getYear());
        car.setValue(request.getValue());

        return CarDetailResponse.fromModel(carRepository.save(car));
    }

	@Transactional(readOnly = true)
    public CarDetailResponse findById(long id) {
        return CarDetailResponse.fromModel(carRepository.findCar(id));
    }
}
