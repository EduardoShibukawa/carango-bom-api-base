package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.exceptions.CarNotFoundException;

public interface CarRepository extends JpaRepository<Car, Long>, JpaSpecificationExecutor<Car> {
	
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
