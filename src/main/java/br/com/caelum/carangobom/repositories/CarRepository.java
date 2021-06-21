package br.com.caelum.carangobom.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.caelum.carangobom.domain.Car;

public interface CarRepository extends JpaRepository<Car, Long> {
	
	List<Car> findAll();

}
