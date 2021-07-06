package br.com.caelum.carangobom.auth.controllers;

import br.com.caelum.carangobom.auth.dtos.AuthRequest;
import br.com.caelum.carangobom.auth.dtos.TokenResponse;
import br.com.caelum.carangobom.auth.dtos.ValidTokenRequest;
import br.com.caelum.carangobom.auth.dtos.ValidTokenResponse;
import br.com.caelum.carangobom.auth.services.TokenService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class AuthControllerTest {

	private AuthController authController;
	
	@Mock
	private AuthenticationManager authenticationManagerMock;
	
	@Mock
	private TokenService tokenServiceMock;
	
	
	@BeforeEach
	void setUp() {
		openMocks(this);
		
		authController = new AuthController(authenticationManagerMock, tokenServiceMock);
	}
	
	@Test
	void whenAuthenticate_shouldReturnToken() {
		AuthRequest authRequest = new AuthRequest("eduardo", "123456");
		
		
		when(tokenServiceMock.createToken(any()))
			.thenReturn("TOKEN");
		
		ResponseEntity<TokenResponse> response 
			= authController.auth(authRequest);
		
		assertEquals(HttpStatus.OK, response.getStatusCode());
		assertEquals("TOKEN", response.getBody().getToken());
		
		
		verify(authenticationManagerMock).authenticate(any());
		verify(tokenServiceMock).createToken(any());
	}

	@Test
	void whenNotAuthenticate_shouldReturnBadResponse() {
		AuthRequest authRequest = new AuthRequest("eduardo", "123456");
		
		doThrow(BadCredentialsException.class)
			.when(authenticationManagerMock)
			.authenticate(any());		
		
		ResponseEntity<TokenResponse> response 
			= authController.auth(authRequest);
		
		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
		
	@Test
	void whenVerifyAndValid_shouldReturnOk() {
		when(tokenServiceMock.isTokenValid(any()))
			.thenReturn(true);
		
		ResponseEntity<ValidTokenResponse> response
			= authController.verify(new ValidTokenRequest("TOKEN"));
		
		assertTrue(response.getBody().isValid());
		
		verify(tokenServiceMock).isTokenValid("TOKEN");
	}

	@Test
	void whenVerifyAndNotValid_shouldReturnBadRequest() {
		when(tokenServiceMock.isTokenValid(any()))
			.thenReturn(false);
		
		ResponseEntity<ValidTokenResponse> response
			= authController.verify(new ValidTokenRequest("TOKEN"));
		
		assertFalse(response.getBody().isValid());
		
		verify(tokenServiceMock).isTokenValid("TOKEN");
	}

}
