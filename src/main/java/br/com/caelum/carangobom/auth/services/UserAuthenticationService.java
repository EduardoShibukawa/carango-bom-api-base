package br.com.caelum.carangobom.auth.services;

import br.com.caelum.carangobom.users.repositories.UserRepository;
import br.com.caelum.carangobom.users.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserAuthenticationService implements UserDetailsService {
	
	private UserRepository userRepository;
	
	@Autowired
	public UserAuthenticationService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
		Optional<User> user = userRepository.findByUsername(username);
		
		return user.orElseThrow(()-> new UsernameNotFoundException("user not found."));	
	}

}
