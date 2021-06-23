package br.com.caelum.carangobom.repositories;

import br.com.caelum.carangobom.domain.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByUserName(String userName);
    User save(User user);
}
