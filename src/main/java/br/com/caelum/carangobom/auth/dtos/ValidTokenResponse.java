package br.com.caelum.carangobom.auth.dtos;

import lombok.Value;

@Value
public class ValidTokenResponse {
	boolean valid;

	public ValidTokenResponse(boolean valid) {
		this.valid = valid;
	}
}
