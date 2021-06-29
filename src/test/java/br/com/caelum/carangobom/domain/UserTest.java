package br.com.caelum.carangobom.domain;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

import org.hamcrest.FeatureMatcher;
import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.GrantedAuthority;

class UserTest {

	@Test
	void whenCreateUserShouldHaveDefaultAttributes() {
		final User user = new User(1L, "eduardo", "123456");

		assertEquals(user.getId(), 1L);
		assertEquals(user.getUsername(), "eduardo");
		assertEquals(user.getPassword(), "123456");
		assertEquals(user.isAccountNonExpired(), true);
		assertEquals(user.isAccountNonLocked(), true);
		assertEquals(user.isCredentialsNonExpired(), true);
		assertEquals(user.isEnabled(), true);
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
