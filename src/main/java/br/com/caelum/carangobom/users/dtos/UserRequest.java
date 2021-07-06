package br.com.caelum.carangobom.users.dtos;

import br.com.caelum.carangobom.users.entities.User;
import lombok.Value;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Value
public class UserRequest {
	
    @NotBlank
    @Size(min=5)
    String username;

    @NotBlank
    @Size(min=5)
    String password;

    public User toModel(){
        return new User(this.username, new BCryptPasswordEncoder().encode(this.password));
    }


    public User updateModel(User user){
        user.setUsername(this.username);
        user.setPassword(new BCryptPasswordEncoder().encode(this.password));
        return user;
    }


}
