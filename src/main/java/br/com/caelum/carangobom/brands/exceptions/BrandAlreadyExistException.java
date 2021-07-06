package br.com.caelum.carangobom.brands.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(code = HttpStatus.CONFLICT, reason = "can not have two brands with same name")
public class BrandAlreadyExistException extends RuntimeException {

	private static final long serialVersionUID = 597528949940023399L;

	public BrandAlreadyExistException() {
		super("can not have two brands with same name");
	}


}
