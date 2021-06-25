package br.com.caelum.carangobom.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.repositories.UserRepository;

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
