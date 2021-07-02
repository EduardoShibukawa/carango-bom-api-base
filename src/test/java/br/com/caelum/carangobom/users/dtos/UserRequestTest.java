package br.com.caelum.carangobom.users.dtos;

import br.com.caelum.carangobom.users.entities.User;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserRequestTest {

	@Test
	void shouldCreateUserObjectWithEncryptedPassword() {
		UserRequest userRequest = new UserRequest("user", "123456");
		User user = userRequest.toModel();
		
		assertEquals(userRequest.getUsername(), user.getUsername());
		assertTrue(new BCryptPasswordEncoder().matches(userRequest.getPassword(), user.getPassword()));
	}
}

