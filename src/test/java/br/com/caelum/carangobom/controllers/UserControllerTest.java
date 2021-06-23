package br.com.caelum.carangobom.controllers;

import br.com.caelum.carangobom.dtos.UserDetailResponse;
import br.com.caelum.carangobom.dtos.UserRequest;
import br.com.caelum.carangobom.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class UserControllerTest {

	private UserController userController;
	private UriComponentsBuilder uriBuilder;

	@Mock
	private UserService userService;

	@BeforeEach
	public void configureMock() {
		openMocks(this);

		userController = new UserController(userService);
		uriBuilder = UriComponentsBuilder.fromUriString("http://localhost:8080");
	}

	@Test
	void shouldCreateUser() {
		UserRequest userRequest = new UserRequest("eduardo", "654321");
		UserDetailResponse userDetailResponse = new UserDetailResponse(1l, "eduardo");

		when(userService.save(userRequest))
				.thenReturn(userDetailResponse);

		ResponseEntity<UserDetailResponse> responseEntity = userController.save(userRequest, uriBuilder);

		assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
		assertEquals(userDetailResponse, responseEntity.getBody());

		assertEquals("http://localhost:8080/users/1", responseEntity.getHeaders().getLocation().toString());

		verify(userService).save(Mockito.any(UserRequest.class));
	}

	@Test
	void whenCreateUserAndExistAnotherUserWithSameUserName_shouldThrowUserAlreadyExistException() {
		UserRequest userRequest = new UserRequest("eduardo", "654321");

		doThrow(UserAlreadyExistException.class)
				.when(userService).save(userRequest);

		ResponseEntity<UserDetailResponse> responseEntity = userController.save(userRequest, uriBuilder);

		assertEquals(HttpStatus.CONFLICT, responseEntity.getStatusCode());
	}
}