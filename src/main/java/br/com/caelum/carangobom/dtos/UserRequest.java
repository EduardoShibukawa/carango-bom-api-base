package br.com.caelum.carangobom.dtos;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import br.com.caelum.carangobom.domain.User;
import lombok.Value;

@Value
public class UserRequest {
	
    @NotBlank
    @Size(min=5)
    String userName;

    @NotBlank
    @Size(min=5)
    String password;

    public User toModel(){
        return new User(this.userName, this.password);
    }
}
