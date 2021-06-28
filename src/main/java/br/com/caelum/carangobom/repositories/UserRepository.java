package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.caelum.carangobom.domain.User;

public interface UserRepository extends Repository<User, Long> {
	
	User save(User user);
	
	List<User> findAll();
	
	Optional<User> findById(Long id);
    
	Optional<User> findByUsername(String userName);
	
	boolean existsByUsername(String userName);
	
}
