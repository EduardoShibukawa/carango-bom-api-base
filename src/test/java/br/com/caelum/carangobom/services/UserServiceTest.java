package br.com.caelum.carangobom.services;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.allOf;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.hasProperty;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.dtos.UserDetailResponse;
import br.com.caelum.carangobom.dtos.UserRequest;
import br.com.caelum.carangobom.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.exceptions.UserNotFoundException;
import br.com.caelum.carangobom.repositories.UserRepository;

class UserServiceTest {

	private UserService userService;

	@Mock
	private UserRepository userRepositoryMock;
	
	@BeforeEach
	public void setUp() {
        openMocks(this);
		this.userService = new UserService(userRepositoryMock);
	}

	@Test
	void findAll_shouldReturnUsersDetail() {
		List<User> users = List.of(
				new User(1L, "eduardo", "123456"),
				new User(2L, "lucas", "654321"));
		
		when(userRepositoryMock.findAll())
			.thenReturn(users);
		
		List<UserDetailResponse> result = userService.getAll();

        assertThat(result, hasSize(2));
        assertThat(result, contains(
        		allOf(
    				hasProperty("id", is(1L)), 
    				hasProperty("username", is("eduardo"))
						
				),
        		allOf(
    				hasProperty("id", is(2L)), 
    				hasProperty("username", is("lucas"))
				)
		));
	}
	
	@Test
	void whenCreateANewUserAndUserNotExist_shouldReturnUserDetails() {
		UserRequest userRequest = new UserRequest("lucas", "123456");
		User user = new User(1L, "lucas", "123456");

		when(userRepositoryMock.existsByUsername("lucas"))
				.thenReturn(false);

		when(userRepositoryMock.save(any()))
				.thenReturn(user);

		UserDetailResponse userDetailResponse = userService.save(userRequest);

		assertEquals(userRequest.getUsername(), userDetailResponse.getUsername());
		assertEquals(1L, userDetailResponse.getId());
	}

	@Test
	void whenCreateANewUserAndUserAlreadyExist_shouldReturnUserDetails() {
		UserRequest userRequest = new UserRequest("lucas", "123456");

		when(userRepositoryMock.existsByUsername("lucas"))
				.thenReturn(true);

		assertThrows(UserAlreadyExistException.class, () -> userService.save(userRequest));
	}
	
	@Test
	void should_delete() {
		when(userRepositoryMock.findUser(1l))
			.thenReturn(new User());
		
		userService.delete(1L);
		
		verify(userRepositoryMock).delete(any());
	}
	
	@Test
	void whenDeleteAndUserNotExists_shouldThrowUserNotFoundException() {
		doThrow(UserNotFoundException.class)
			.when(userRepositoryMock)
			.findUser(1L);
		
		assertThrows(UserNotFoundException.class, () -> userService.delete(1L));
		
		verify(userRepositoryMock, never()).delete(any());
	}
}
