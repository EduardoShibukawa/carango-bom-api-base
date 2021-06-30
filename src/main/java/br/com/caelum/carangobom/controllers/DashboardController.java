package br.com.caelum.carangobom.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.dtos.CarPriceByBrandItemResponse;
import br.com.caelum.carangobom.services.DashboardService;


@RestController
@RequestMapping("dashboard")
public class DashboardController {
	
	private final DashboardService dashboardService;
	
	@Autowired
	public DashboardController(DashboardService dashboardService) {
		this.dashboardService = dashboardService;
	}

	@GetMapping("cars-by-brand")	
	public ResponseEntity<List<CarPriceByBrandItemResponse>> totalPriceCarsByBrand() {
		return ResponseEntity.ok(dashboardService.totalPriceCarsByBrand());
	}

}
