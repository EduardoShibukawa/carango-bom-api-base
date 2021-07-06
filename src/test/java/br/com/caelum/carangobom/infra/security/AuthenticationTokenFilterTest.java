package br.com.caelum.carangobom.infra.security;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.io.IOException;
import java.util.Optional;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import br.com.caelum.carangobom.auth.services.TokenService;
import br.com.caelum.carangobom.users.entities.User;

class AuthenticationTokenFilterTest {

	private AuthenticationTokenFilter authenticationTokenFilter;
	
	@Mock
	private TokenService tokenServiceMock;
	
	@Mock
	private HttpServletRequest requestMock;
	
	@Mock
	private HttpServletResponse responseMock;
	
	@Mock
	FilterChain filterChainMock;
	
	@BeforeEach
	void setUp() {
		openMocks(this);
		authenticationTokenFilter = new AuthenticationTokenFilter(tokenServiceMock);
	}
		
	@Test
	void whenTokenIsValid_shouldSetAuthenticationInContext() throws ServletException, IOException {
		final User user = new User(1L, "eduardo", "123456");
		
		when(tokenServiceMock.getUser(any()))
			.thenReturn(Optional.of(user));
		
		when(tokenServiceMock.isTokenValid(any()))
			.thenReturn(true);
		
		authenticationTokenFilter.doFilterInternal(requestMock, responseMock, filterChainMock);
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		assertNotNull(authentication);		
		assertNotNull(authentication.getPrincipal());
		assertEquals(user, ((User) authentication.getPrincipal()));
	}

	
	@Test
	void whenTokenIsNotValid_shouldNotSetAuthenticationInContext() throws ServletException, IOException {
		when(tokenServiceMock.isTokenValid(any()))
			.thenReturn(false);
		
		authenticationTokenFilter.doFilterInternal(requestMock, responseMock, filterChainMock);
		
		final Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		
		assertNull(authentication);
	}

}
