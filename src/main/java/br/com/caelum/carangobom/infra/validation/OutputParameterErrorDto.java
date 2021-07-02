package br.com.caelum.carangobom.infra.validation;

import lombok.Value;

@Value
public class OutputParameterErrorDto {
	String parameter;
	String message;
}
