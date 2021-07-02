package br.com.caelum.carangobom.auth.services;

import br.com.caelum.carangobom.users.entities.User;
import br.com.caelum.carangobom.users.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Clock;
import java.util.Date;
import java.util.Locale;
import java.util.Optional;
import java.util.TimeZone;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.openMocks;

class TokenServiceTest {

	private final static String VALID_TOKEN = "eyJhbGciOiJIUzI1NiJ9.eyJpc3MiOiJjYXJhbmdvLWJvbS1hcGkiLCJzdWIiOiIxIiwiaWF0IjoxNjI1MTQ0Mzg3LCJleHAiOjE2MjUyMzA3ODd9.hROjAcAsYtiRpWFFQmxaDeZIPB0EOg1-FCCux5-YB6E";  
	
	private TokenService tokenService;
	
	@Mock
	private UserRepository userRepositoryMock;
	
	@BeforeEach
	void setUp() throws ParseException {
        openMocks(this);

		String secret = "rm'!@N=Ke!~p8VTA2ZRK~nMDQX5Uvm!m'D&]{@Vr?G;2?XhbC:Qa#9#eMLN\\}x3?JR3.2zr~v)gYF^8\\:8>:XfB:Ww75N/emt9Yj[bQMNCWwW\\J?N,nvH.<2\\.r~w]*e~vgak)X\"v8H`MH/7\"2E`,^k@n<vE-wD3g9JWPy;CrY*.Kd2_D])=><D?YhBaSua5hW%{2]_FVXzb9`8FH^b[X3jzVER&:jw2<=c38=>L/zBq`}C6tT*cCSVC^c]-L}&/";
		String expiration = "86400000";

		SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy", Locale.ENGLISH);
		String dateInString = "1-7-2021";
		Date date = formatter.parse(dateInString) ;

		this.tokenService = new TokenService(secret, expiration, userRepositoryMock, () -> date);
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

	private static Clock CLOCK = Clock.systemDefaultZone();
	private static final TimeZone REAL_TIME_ZONE = TimeZone.getDefault();

	@Test
	void shouldGetUser() {
		final User user = new User(1L, "eduardo", "123456");
		
		when(userRepositoryMock.findById(1L))
			.thenReturn(Optional.of(user));

		Optional<User> opUser = tokenService.getUser(VALID_TOKEN);
		
		assertEquals(user, opUser.get());
	}
}
