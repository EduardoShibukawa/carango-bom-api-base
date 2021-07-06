package br.com.caelum.carangobom.users.controllers;

import br.com.caelum.carangobom.users.dtos.UserDetailResponse;
import br.com.caelum.carangobom.users.dtos.UserRequest;
import br.com.caelum.carangobom.users.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.users.exceptions.UserNotFoundException;
import br.com.caelum.carangobom.users.services.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;

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
	
	@Test
	void shouldReturnUsersWhenExists() {
		List<UserDetailResponse> responses = List.of(
				new UserDetailResponse(1l, "eduardo"),
				new UserDetailResponse(2l, "lucas"));
		
		
		when(userService.getAll())
			.thenReturn(responses);
		
		ResponseEntity<List<UserDetailResponse>> result = userController.getAll();
		
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(responses, result.getBody());
	}
	
	@Test
	void shouldDeleteUser() {
		ResponseEntity<Void> result = userController.delete(1L);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		
		verify(userService).delete(1l);
	}
	
	@Test
	void whenDeleteAndCarNotExist_shouldReturnStatusNotFound() {
		doThrow(UserNotFoundException.class)
			.when(userService)
			.delete(1L);
		
		ResponseEntity<Void> result = userController.delete(1L);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		
		verify(userService).delete(1L);
	}
	
	@Test
	void shouldFindUseById() {
		UserDetailResponse userDetailResponse
			= new UserDetailResponse(1L, "eduardo");
		
		when(userService.findById(1L))
		  .thenReturn(userDetailResponse);
		
		ResponseEntity<UserDetailResponse> result = userController.findById(1L);
		
		assertEquals(HttpStatus.OK, result.getStatusCode());
		assertEquals(userDetailResponse, result.getBody());
		
		verify(userService).findById(1L);
	}
	
	@Test
	void whenFindByIdAndUserNotExist_shouldReturnStatusNotFound() {
		doThrow(UserNotFoundException.class)
			.when(userService)
			.findById(1L);
		
		ResponseEntity<UserDetailResponse> result = userController.findById(1L);
		
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		
		verify(userService).findById(1L);
	}

	@Test
	void whenUpdateAndUsernameAlreadyExist_shouldReturnConflict(){
		UserRequest userRequest = new UserRequest("newusername", "newpassword");

		doThrow(UserAlreadyExistException.class).when(userService).update(1L, userRequest);

		ResponseEntity<UserDetailResponse> response = userController.update(1L, userRequest);
		assertEquals(HttpStatus.CONFLICT, response.getStatusCode());
	}

	@Test
	void whenUpdateAndUserNotExists_shouldReturnNotFound(){
		UserRequest userRequest = new UserRequest("newusername", "newpassword");

		doThrow(UserNotFoundException.class).when(userService).update(1L, userRequest);
		ResponseEntity<UserDetailResponse> response = userController.update(1L, userRequest);

		assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
	}

	@Test
	void whenUpdate_shouldReturnOk(){
		UserRequest userRequest = new UserRequest("newusername", "newpassword");

		ResponseEntity<UserDetailResponse> response = userController.update(1L, userRequest);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}