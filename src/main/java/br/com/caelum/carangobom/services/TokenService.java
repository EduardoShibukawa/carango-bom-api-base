package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    @Value("${carango-bom-api.jwt.secret}")
    private String secret;

    @Value("${carango-bom-api.jwt.expiration}")
    private String expiration;

    private UserRepository userRepository;
    
    @Autowired
    public TokenService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
    
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

	public boolean isTokenValid(String token) {
		try {
			Jwts.parser()
				.setSigningKey(this.secret)
				.parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Optional<User> getUser(String token) {
		Claims claims = Jwts.parser()
			.setSigningKey(this.secret)
			.parseClaimsJws(token)
			.getBody();

		return userRepository.findById(
				Long.parseLong(claims.getSubject()));
	}
}
