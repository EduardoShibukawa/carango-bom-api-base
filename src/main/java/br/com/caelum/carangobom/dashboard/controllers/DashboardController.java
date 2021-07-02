package br.com.caelum.carangobom.dashboard.controllers;

import br.com.caelum.carangobom.dashboard.dtos.CarPriceByBrandItemResponse;
import br.com.caelum.carangobom.dashboard.services.DashboardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@RestController
@RequestMapping("dashboard")
@CrossOrigin(origins = "*", maxAge = 3600)
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
