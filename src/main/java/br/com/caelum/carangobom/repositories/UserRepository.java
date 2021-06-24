package br.com.caelum.carangobom.repositories;

import org.springframework.data.repository.Repository;

import br.com.caelum.carangobom.domain.User;

public interface UserRepository extends Repository<User, Long> {
	
	User save(User user);
    
	boolean existsByUserName(String userName);
}
