package br.com.caelum.carangobom.users.services;

import br.com.caelum.carangobom.users.dtos.UserDetailResponse;
import br.com.caelum.carangobom.users.dtos.UserRequest;
import br.com.caelum.carangobom.users.entities.User;
import br.com.caelum.carangobom.users.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.users.repositories.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class UserService {

    private UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailResponse save(UserRequest userRequest) {
        if (userRepository.existsByUsername(userRequest.getUsername()))
            throw new UserAlreadyExistException();

        return UserDetailResponse.fromModel(userRepository.save(userRequest.toModel()));
    }

    public UserDetailResponse update(Long id, UserRequest userRequest) {
        User user = userRepository.findUser(id);

        if (!user.getUsername().equals(userRequest.getUsername())
                && userRepository.existsByUsername(userRequest.getUsername()))
            throw new UserAlreadyExistException();

        return UserDetailResponse.fromModel(userRepository.save(userRequest.updateModel(user)));
    }

    @Transactional(readOnly = true)
	public List<UserDetailResponse> getAll() {
		return userRepository.findAll()
				.stream()
				.map(UserDetailResponse::fromModel)
				.collect(Collectors.toUnmodifiableList());
	}
    
    @Transactional(readOnly = true)
    public UserDetailResponse findById(Long id) {
    	return UserDetailResponse.fromModel(userRepository.findUser(id));
    }

	public void delete(long id) {
		userRepository.delete(
				userRepository.findUser(id));
	}
}
