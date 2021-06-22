package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarDetailResponse;
import br.com.caelum.carangobom.dtos.CarRequest;
import br.com.caelum.carangobom.repositories.CarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

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

    public CarDetailResponse findById(long id) {
        return CarDetailResponse.fromModel(carRepository.findCar(id));
    }
}
