package br.com.caelum.carangobom.dtos;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import br.com.caelum.carangobom.domain.User;

class UserRequestTest {

	@Test
	void shouldCreateUserObjectWithEncryptedPassword() {
		UserRequest userRequest = new UserRequest("user", "123456");
		User user = userRequest.toModel();
		
		assertEquals(userRequest.getUsername(), user.getUsername());
		assertTrue(new BCryptPasswordEncoder().matches(userRequest.getPassword(), user.getPassword()));
	}
}

