package br.com.caelum.carangobom.users.services;

import br.com.caelum.carangobom.users.dtos.UserDetailResponse;
import br.com.caelum.carangobom.users.repositories.UserRepository;
import br.com.caelum.carangobom.users.dtos.UserRequest;
import br.com.caelum.carangobom.users.exceptions.UserAlreadyExistException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
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

	public List<UserDetailResponse> getAll() {
		return userRepository.findAll()
				.stream()
				.map(UserDetailResponse::fromModel)
				.collect(Collectors.toUnmodifiableList());
	}

	public void delete(long id) {
		userRepository.delete(
				userRepository.findUser(id));
	}
}
