package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.User;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class TokenService {

    @Value("${carango-bom-api.jwt.secret}")
    private String secret;

    @Value("${carango-bom-api.jwt.expiration}")
    private String expiration;

    public String createToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date date = new Date();
        return Jwts.builder()
                .setIssuer("carango-bom-api")
                .setSubject(user.getId().toString())
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime() + Long.parseLong(expiration)))
                .signWith(SignatureAlgorithm.HS256, secret)
                .compact();

    }
}
