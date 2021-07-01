package br.com.caelum.carangobom.services;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.repositories.UserRepository;

class TokenServiceTest {

	private final static String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjYXJhbmdvLWJvbS1hcGkiLCJzdWIiOiIxIiwiaWF0IjoxNjI1MTQ0Mzg3LCJleHAiOjE2MjUyMzA3ODd9.hROjAcAsYtiRpWFFQmxaDeZIPB0EOg1-FCCux5-YB6E";  
	
	private TokenService tokenService;
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@BeforeEach
	void setUp() {
        openMocks(this);

		String secret = "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\\}x3?JR3.2zr~v)gYF^8\\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\\J?N,nvH.<2\\.r~w]*e~vgak)X\"v8H`MH/7\"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/";
		String expiration = "86400000";
		
		this.tokenService = new TokenService(secret, expiration, userRepositoryMock);
	}

	@Test
	void tokenShouldBeValid() {
		assertTrue(this.tokenService.isTokenValid(VALID_TOKEN));
	}
	
	@Test
	void tokenShouldNotBeValid() {
		assertFalse(this.tokenService.isTokenValid(VALID_TOKEN.substring(0, VALID_TOKEN.length() - 1)));
	}
	
	@Test
	void shouldCreateToken() {
		final User user = new User(1L, "eduardo", "123456");
		final Authentication authentication = new TestingAuthenticationToken(user, user.getPassword());

		String token = this.tokenService.createToken(authentication);
		
		assertNotNull(token);
		assertTrue(this.tokenService.isTokenValid(token));
	}
	
	@Test
	void shouldGetUSer() {
		final User user = new User(1L, "eduardo", "123456");
		
		when(userRepositoryMock.findById(1L))
			.thenReturn(Optional.of(user));
		
		Optional<User> opUser = tokenService.getUser(VALID_TOKEN);
		
		assertEquals(user, opUser.get());
	}


}
