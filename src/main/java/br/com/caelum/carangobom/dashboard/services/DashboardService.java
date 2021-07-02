package br.com.caelum.carangobom.dashboard.services;

import br.com.caelum.carangobom.brands.entities.Brand;
import br.com.caelum.carangobom.cars.entities.Car;
import br.com.caelum.carangobom.cars.repositories.CarRepository;
import br.com.caelum.carangobom.dashboard.dtos.CarPriceByBrandItemResponse;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

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
