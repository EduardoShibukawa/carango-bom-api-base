package br.com.caelum.carangobom.services;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import br.com.caelum.carangobom.dtos.UserDetailResponse;
import br.com.caelum.carangobom.dtos.UserRequest;
import br.com.caelum.carangobom.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.repositories.UserRepository;

@Service
public class UserService {

    private  UserRepository userRepository;

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
}
