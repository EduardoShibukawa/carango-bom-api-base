package br.com.caelum.carangobom.users.entities;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class UserTest {

	@Test
	void whenCreateUserShouldHaveDefaultAttributes() {
		final User user = new User(1L, "eduardo", "123456");

		assertEquals(1L, user.getId());
		assertEquals("eduardo", user.getUsername());
		assertEquals("123456", user.getPassword());
		assertTrue(user.isAccountNonExpired());
		assertTrue(user.isAccountNonLocked());
		assertTrue(user.isCredentialsNonExpired());
		assertTrue(user.isEnabled());
		assertThat(user.getAuthorities(), hasSize(1));
		assertThat(user.getAuthorities(), hasItem(authoritie(equalTo("admin"))));
	}

	private FeatureMatcher<GrantedAuthority, String> authoritie(Matcher<String> matcher) {
		return new FeatureMatcher<GrantedAuthority, String>(matcher, "authoritie", "authoritie") {

			@Override
			protected String featureValueOf(GrantedAuthority actual) {
				return actual.getAuthority();
			}
		};
	}
}
