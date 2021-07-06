package br.com.caelum.carangobom.users.controllers;

import br.com.caelum.carangobom.users.dtos.UserDetailResponse;
import br.com.caelum.carangobom.users.dtos.UserRequest;
import br.com.caelum.carangobom.users.exceptions.UserAlreadyExistException;
import br.com.caelum.carangobom.users.exceptions.UserNotFoundException;
import br.com.caelum.carangobom.users.services.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

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

    @PutMapping("/{id}")
    public ResponseEntity<UserDetailResponse> update(@PathVariable Long id, @Valid @RequestBody UserRequest request){
        try{
            UserDetailResponse response = userService.update(id, request);
            return ResponseEntity.ok(response);
        }catch (UserAlreadyExistException e){
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }
        catch (UserNotFoundException e){
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
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

    @DeleteMapping("/{id}")
	public ResponseEntity<Void> delete(@PathVariable long id) {
		try {
			userService.delete(id);
			
			return ResponseEntity.ok().build();
		} catch (UserNotFoundException e) {
			return ResponseEntity.notFound().build();
		}
	}
}