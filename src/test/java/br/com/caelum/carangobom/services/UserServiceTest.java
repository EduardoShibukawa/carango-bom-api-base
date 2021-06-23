package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.dtos.UserDetailResponse;
import br.com.caelum.carangobom.dtos.UserRequest;
import br.com.caelum.carangobom.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
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
	void whenCreateANewUserAndUserNotExist_shouldReturnUserDetails() {
		UserRequest userRequest = new UserRequest("lucas", "123456");
		User user = new User(1L, "lucas", "123456");

		when(userRepositoryMock.existsByUserName("lucas"))
				.thenReturn(false);

		when(userRepositoryMock.save(any()))
				.thenReturn(user);

		UserDetailResponse userDetailResponse = userService.save(userRequest);

		assertEquals(userRequest.getUserName(), userDetailResponse.getUserName());
		assertEquals(1L, userDetailResponse.getId());
	}

	@Test
	void whenCreateANewUserAndUserAlreadyExist_shouldReturnUserDetails() {
		UserRequest userRequest = new UserRequest("lucas", "123456");

		when(userRepositoryMock.existsByUserName("lucas"))
				.thenReturn(true);

		assertThrows(UserAlreadyExistException.class, () -> userService.save(userRequest));
	}
}
