package br.com.caelum.carangobom.users.services;

import br.com.caelum.carangobom.users.dtos.UserDetailResponse;
import br.com.caelum.carangobom.users.dtos.UserRequest;
import br.com.caelum.carangobom.users.entities.User;
import br.com.caelum.carangobom.users.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.users.exceptions.UserNotFoundException;
import br.com.caelum.carangobom.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

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
	
	@Test
	void shouldFindById() {
		when(userRepositoryMock.findUser(1L))
			.thenReturn(new User(1L, "eduardo", "123456"));
		
		UserDetailResponse findById = userService.findById(1L);
		
		assertEquals(1L, findById.getId());
		assertEquals("eduardo", findById.getUsername());
	}
	
	@Test
	void whenUserNotFound_shouldThrowError() {
		doThrow(UserNotFoundException.class)
			.when(userRepositoryMock)
			.findUser(1L);
		
		assertThrows(UserNotFoundException.class, () -> userService.findById(1L));
	}

	@Test
	void whenUpdateAndUserNotExists_shouldThrowUserNotFoundException() {
		doThrow(UserNotFoundException.class)
				.when(userRepositoryMock).findUser(1L);

		assertThrows(UserNotFoundException.class,
				() -> userService.update(1L, new UserRequest("username", "password")));
	}

	@Test
	void whenUpdateAndUseSameUsernameOfAnotherUser_shouldThrowUserAlreadyExistException() {
		UserRequest userRequest = new UserRequest("newusername", "password");
		User user = new User("username", "password");

		when(userRepositoryMock.findUser(1L)).thenReturn(user);
		when(userRepositoryMock.existsByUsername(userRequest.getUsername())).thenReturn(true);

		assertThrows(UserAlreadyExistException.class, () -> userService.update(1L, userRequest));
	}

	@Test
	void whenUpdateAndUserExistAndPutSameUsername_shouldReturnUserResponse() {
		UserRequest userRequest = new UserRequest("username", "newpassword");
		User user = new User(1L,"username", "password");

		when(userRepositoryMock.findUser(1L)).thenReturn(user);
		when(userRepositoryMock.existsByUsername(userRequest.getUsername())).thenReturn(true);
		when(userRepositoryMock.save(user)).thenReturn(user);

		UserDetailResponse response = userService.update(1L, userRequest);
		assertEquals(userRequest.getUsername(), response.getUsername());
		assertEquals(1L, response.getId());

		verify(userRepositoryMock).save(user);
	}

	@Test
	void whenUpdateAndPutDifferentUsername_shouldReturnUserResponse() {
		UserRequest userRequest = new UserRequest("new username", "newpassword");
		User user = new User(1L,"username", "password");

		when(userRepositoryMock.findUser(1L)).thenReturn(user);
		when(userRepositoryMock.existsByUsername(userRequest.getUsername())).thenReturn(false);
		when(userRepositoryMock.save(user)).thenReturn(user);

		UserDetailResponse response = userService.update(1L, userRequest);

		assertEquals(1L, response.getId());

		verify(userRepositoryMock).save(user);
	}
}
