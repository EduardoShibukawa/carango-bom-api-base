package br.com.caelum.carangobom.infra.security;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.services.TokenService;

public class AuthenticationTokenFilter extends OncePerRequestFilter {

	private final TokenService tokenService;

	public AuthenticationTokenFilter(TokenService tokenService) {
		this.tokenService = tokenService;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		final String token = retrieveToken(request);
		final boolean valid = tokenService.isTokenValid(token);

		if (valid) {
			Optional<User> user = tokenService.getUser(token);

			user.ifPresent(u -> {
				UsernamePasswordAuthenticationToken authentication 
					= new UsernamePasswordAuthenticationToken(u, null, u.getAuthorities());

				SecurityContextHolder.getContext().setAuthentication(authentication);
			});
		}

		filterChain.doFilter(request, response);
	}

	private String retrieveToken(HttpServletRequest request) {
		Optional<String> authorization = Optional.ofNullable(request.getHeader("Authorization"));

		if (authorization.isEmpty() 
				|| authorization.get().isBlank() 
				|| !authorization.get().startsWith("Bearer ")) {
			return null;
		}

		return authorization.get().replace("Bearer ", "");
	}
}
