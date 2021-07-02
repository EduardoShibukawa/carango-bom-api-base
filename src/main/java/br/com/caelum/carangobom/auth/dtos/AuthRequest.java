package br.com.caelum.carangobom.auth.dtos;

import lombok.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;

@Value
public class AuthRequest {
    String username;
    String password;

    public UsernamePasswordAuthenticationToken createAuthenticationToken(){
        return new UsernamePasswordAuthenticationToken(username, password);
    }
}
