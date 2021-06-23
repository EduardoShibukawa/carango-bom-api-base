package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.User;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class UserRequest {
    @NotBlank
    @Size(min=5)
    private String userName;

    @NotBlank
    @Size(min=5)
    private String password;

    public UserRequest(String userName, String password) {
        this.userName = userName;
        this.password = password;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public User toModel(){
        return new User(this.userName, this.password);
    }
}
