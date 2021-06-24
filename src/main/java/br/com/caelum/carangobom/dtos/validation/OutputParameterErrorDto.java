package br.com.caelum.carangobom.dtos.validation;

public class OutputParameterErrorDto {

	private String parameter;
	private String message;

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parametro) {
		this.parameter = parametro;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String mensagem) {
		this.message = mensagem;
	}
}
