package br.com.caelum.carangobom.infra.validation;

import java.util.List;

import lombok.Value;

@Value
public class OutPutParameterListErrorDto {

	List<OutputParameterErrorDto> errors;

	public int getErroCount() {
		return errors.size();
	}
}
