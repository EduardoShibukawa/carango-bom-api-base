package br.com.caelum.carangobom.dtos.validation;

import lombok.Value;

@Value
public class OutputParameterErrorDto {
	String parameter;
	String message;
}
