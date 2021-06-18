package br.com.caelum.carangobom.validation;

import java.util.List;

public class OutPutParameterListErrorDto {

    private List<OutputParameterErrorDto> errors;

    public int getErroCount() {
        return errors.size();
    }

    public List<OutputParameterErrorDto> getErrors() {
        return errors;
    }

    public void setErrors(List<OutputParameterErrorDto> erros) {
        this.errors = erros;
    }
}
