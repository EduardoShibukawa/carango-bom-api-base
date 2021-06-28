package br.com.caelum.carangobom.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.dtos.UserDetailResponse;
import br.com.caelum.carangobom.dtos.UserRequest;
import br.com.caelum.carangobom.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.services.UserService;

@RestController
@RequestMapping("users")
public class UserController {

    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserDetailResponse> save(@Valid @RequestBody UserRequest request, UriComponentsBuilder uriBuilder){
        try{
            UserDetailResponse response = userService.save(request);
            URI uri = uriBuilder
                    .path("/users/{id}")
                    .buildAndExpand(response.getId())
                    .toUri();

            return ResponseEntity.created(uri).body(response);
        }catch (UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
    }

    @GetMapping
	public ResponseEntity<List<UserDetailResponse>> getAll() {
		return ResponseEntity.ok(userService.getAll());
	}
}