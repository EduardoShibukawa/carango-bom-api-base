package br.com.caelum.carangobom.controllers;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;

import br.com.caelum.carangobom.dtos.AuthRequest;
import br.com.caelum.carangobom.dtos.TokenResponse;
import br.com.caelum.carangobom.services.TokenService;

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

}
