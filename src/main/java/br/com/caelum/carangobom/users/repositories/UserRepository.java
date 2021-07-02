package br.com.caelum.carangobom.users.repositories;

import br.com.caelum.carangobom.users.entities.User;
import br.com.caelum.carangobom.users.exceptions.UserNotFoundException;
import org.springframework.data.repository.Repository;

import java.util.List;
import java.util.Optional;

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
