package br.com.caelum.carangobom.repositories;

import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.exceptions.CarNotFoundException;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface CarRepository extends JpaRepository<Car, Long> {
	
	List<Car> findAll();

	default Car findCar(Long id){
		Optional<Car> optional = findById(id);

		if (optional.isEmpty()) {
			throw new CarNotFoundException();
		}

		return optional.get();
	}
}
