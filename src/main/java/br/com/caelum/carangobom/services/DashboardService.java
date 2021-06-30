package br.com.caelum.carangobom.services;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.domain.Brand;
import br.com.caelum.carangobom.domain.Car;
import br.com.caelum.carangobom.dtos.CarPriceByBrandItemResponse;
import br.com.caelum.carangobom.repositories.CarRepository;

@Service
public class DashboardService {

	private final CarRepository carRepository;

	public DashboardService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}

	public List<CarPriceByBrandItemResponse> totalPriceCarsByBrand() {
		Map<Brand, BigDecimal> totalByBrand 
			= carRepository.findAll()
					.stream()
					.collect(Collectors
						.groupingBy(
							Car::getBrand,
							Collectors.reducing(BigDecimal.ZERO, Car::getValue, BigDecimal::add))
					);
		
		Map<Brand, Long> countByBrand 
			= carRepository.findAll()
					.stream()
					.collect(Collectors
						.groupingBy(
							Car::getBrand,
							Collectors.counting())
					);
		
		return totalByBrand
				.entrySet().stream()
				.map(e -> 
					new CarPriceByBrandItemResponse(
				  			e.getKey().getName(), 
				  			e.getValue(),
				  			countByBrand.get(e.getKey())
		  			)  	
				).collect(Collectors.toUnmodifiableList());		
	}

}
