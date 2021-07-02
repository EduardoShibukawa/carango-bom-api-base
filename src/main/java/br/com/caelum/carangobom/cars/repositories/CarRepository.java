package br.com.caelum.carangobom.cars.repositories;

import br.com.caelum.carangobom.cars.entities.Car;
import br.com.caelum.carangobom.cars.exceptions.CarNotFoundException;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends Repository<Car, Long>, JpaSpecificationExecutor<Car> {
    
    void delete(Car car);
    
    Car save(Car car);
    
    Optional<Car> findById(Long id);
    
	List<Car> findAll();
	
	List<Car> findAll(Specification<Car> spec);

	default Car findCar(Long id){
		Optional<Car> optional = findById(id);

		if (optional.isEmpty()) {
			throw new CarNotFoundException();
		}

		return optional.get();
	}
}
