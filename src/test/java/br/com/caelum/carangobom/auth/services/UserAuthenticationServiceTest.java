package br.com.caelum.carangobom.auth.services;

import br.com.caelum.carangobom.users.entities.User;
import br.com.caelum.carangobom.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class UserAuthenticationServiceTest {

	private UserAuthenticationService userAuthenticationService;

	@Mock
	private UserRepository userRepositoryMock;

	@BeforeEach
	void setUp() {
		openMocks(this);

		this.userAuthenticationService = new UserAuthenticationService(userRepositoryMock);
	}

	@Test
	void shouldReturnUserByName() {
		final User user = new User("eduardo", "123456");

		when(userRepositoryMock.findByUsername("eduardo"))
			.thenReturn(Optional.of(user));
			

		UserDetails loadUserByUsername = this.userAuthenticationService.loadUserByUsername("eduardo");

		assertEquals(user, loadUserByUsername);
	}

	@Test
	void whenUserNotFound_shouldThrowUsernameNotFoundException() {
		when(userRepositoryMock.findByUsername("eduardo"))
			.thenReturn(Optional.empty());

		assertThrows(UsernameNotFoundException.class,
				() -> this.userAuthenticationService.loadUserByUsername("eduardo"));
	}
}
