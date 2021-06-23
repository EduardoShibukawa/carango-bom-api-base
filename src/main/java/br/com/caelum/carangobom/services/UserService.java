package br.com.caelum.carangobom.services;

import br.com.caelum.carangobom.dtos.UserDetailResponse;
import br.com.caelum.carangobom.dtos.UserRequest;
import br.com.caelum.carangobom.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.repositories.UserRepository;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    private  UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public UserDetailResponse save(UserRequest userRequest) {
        if (userRepository.existsByUserName(userRequest.getUserName()))
            throw new UserAlreadyExistException();

        return UserDetailResponse.fromModel(userRepository.save(userRequest.toModel()));
    }
}
