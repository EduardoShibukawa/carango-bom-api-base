package br.com.caelum.carangobom.dtos;

import java.math.BigDecimal;

import lombok.Value;

@Value
public class CarPriceByBrandItemResponse {

	String brand;
	BigDecimal totalPrice;
	Long count;

}
