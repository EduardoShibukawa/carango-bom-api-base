package br.com.caelum.carangobom.repositories;

import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.caelum.carangobom.domain.User;

public interface UserRepository extends Repository<User, Long> {
	
	User save(User user);
    
	boolean existsByUsername(String userName);
	
	Optional<User> findByUsername(String userName);
}
