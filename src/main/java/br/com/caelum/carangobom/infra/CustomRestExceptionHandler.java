package br.com.caelum.carangobom.infra;

import java.util.ArrayList;
import java.util.List;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import br.com.caelum.carangobom.dtos.validation.OutPutParameterListErrorDto;
import br.com.caelum.carangobom.dtos.validation.OutputParameterErrorDto;

@ControllerAdvice
public class CustomRestExceptionHandler extends ResponseEntityExceptionHandler {

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {
        List<OutputParameterErrorDto> paramExceptionList = new ArrayList<>();
        OutPutParameterListErrorDto exceptionList = new OutPutParameterListErrorDto();
        
        ex.getBindingResult()
        	.getFieldErrors()
        	.forEach(e -> {
	            OutputParameterErrorDto d = new OutputParameterErrorDto();
	            
	            d.setParameter(e.getField());
	            d.setMessage(e.getDefaultMessage());
	            
	            paramExceptionList.add(d);
	        });
        
        
        exceptionList.setErrors(paramExceptionList);
        
        return handleExceptionInternal(ex, exceptionList, headers, status, request);
	}
}
