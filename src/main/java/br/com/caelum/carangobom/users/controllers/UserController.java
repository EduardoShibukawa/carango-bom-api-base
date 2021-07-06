package br.com.caelum.carangobom.users.controllers;

import java.net.URI;
import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.caelum.carangobom.users.dtos.UserDetailResponse;
import br.com.caelum.carangobom.users.dtos.UserRequest;
import br.com.caelum.carangobom.users.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.users.exceptions.UserNotFoundException;
import br.com.caelum.carangobom.users.services.UserService;

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
    
    @GetMapping("/{id}")
    public ResponseEntity<UserDetailResponse> findById(@PathVariable Long id) {
    	try {
    		return ResponseEntity.ok(userService.findById(id));    		
    	} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
    }

    @DeleteMapping
	public ResponseEntity<Void> delete(long id) {
		try {
			userService.delete(id);
			
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}