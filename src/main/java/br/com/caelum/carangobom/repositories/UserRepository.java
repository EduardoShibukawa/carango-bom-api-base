package br.com.caelum.carangobom.repositories;

import java.util.List;
import java.util.Optional;

import org.springframework.data.repository.Repository;

import br.com.caelum.carangobom.domain.User;
import br.com.caelum.carangobom.exceptions.UserNotFoundException;

public interface UserRepository extends Repository<User, Long> {
	
	User save(User user);
	
	List<User> findAll();
	
	Optional<User> findById(Long id);
    
	Optional<User> findByUsername(String userName);
	
	boolean existsByUsername(String userName);

	void delete(User user);

	default User findUser(long id) {
		Optional<User> user = findById(id);
		
		return user.orElseThrow(UserNotFoundException::new);
	}
	
}
