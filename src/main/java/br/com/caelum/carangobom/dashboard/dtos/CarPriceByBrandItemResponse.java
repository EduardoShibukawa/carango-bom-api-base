package br.com.caelum.carangobom.dashboard.dtos;

import lombok.Value;

import java.math.BigDecimal;

@Value
public class CarPriceByBrandItemResponse {

	String brand;
	BigDecimal totalPrice;
	Long count;

}
