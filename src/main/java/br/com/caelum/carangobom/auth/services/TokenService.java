package br.com.caelum.carangobom.auth.services;

import br.com.caelum.carangobom.users.entities.User;
import br.com.caelum.carangobom.users.repositories.UserRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Clock;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.impl.DefaultClock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.Optional;

@Service
public class TokenService {

    private final String secret;

    private final String expiration;

    private final UserRepository userRepository;

    private Clock clock;

	@Autowired
	public TokenService(
			@Value("${carango-bom-api.jwt.secret}") String secret,
			@Value("${carango-bom-api.jwt.expiration}") String expiration,
			UserRepository userRepository) {
		this.secret = secret;
		this.expiration = expiration;
		this.userRepository = userRepository;
		this.clock = DefaultClock.INSTANCE;
	}

	public TokenService(
			@Value("${carango-bom-api.jwt.secret}") String secret,
			@Value("${carango-bom-api.jwt.expiration}") String expiration,
			UserRepository userRepository,
			Clock clock) {
		this.secret = secret;
		this.expiration = expiration;
		this.userRepository = userRepository;
		this.clock = clock;
	}
    
    public String createToken(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        Date date = clock.now();
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
				.setClock(this.clock)
				.setSigningKey(this.secret)
				.parseClaimsJws(token);
			
			return true;
		} catch (Exception e) {
			return false;
		}
	}

	public Optional<User> getUser(String token) {
		Claims claims = Jwts.parser()
			.setClock(this.clock)
			.setSigningKey(this.secret)
			.parseClaimsJws(token)
			.getBody();

		return userRepository.findById(
				Long.parseLong(claims.getSubject()));
	}
}
