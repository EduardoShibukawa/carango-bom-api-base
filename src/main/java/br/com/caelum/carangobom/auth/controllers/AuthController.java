package br.com.caelum.carangobom.auth.controllers;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.caelum.carangobom.auth.dtos.AuthRequest;
import br.com.caelum.carangobom.auth.dtos.TokenResponse;
import br.com.caelum.carangobom.auth.dtos.ValidTokenRequest;
import br.com.caelum.carangobom.auth.dtos.ValidTokenResponse;
import br.com.caelum.carangobom.auth.services.TokenService;

@RestController
@RequestMapping("auth")
public class AuthController {

    private AuthenticationManager authenticationManager;
    private TokenService tokenService;

    @Autowired
    public AuthController(AuthenticationManager authenticationManager, TokenService tokenService) {
        this.authenticationManager = authenticationManager;
        this.tokenService = tokenService;
    }

    @PostMapping
    public ResponseEntity<TokenResponse> auth(@Valid @RequestBody AuthRequest request){
        try {
            Authentication authentication = authenticationManager.authenticate(request.createAuthenticationToken());
            return ResponseEntity.ok(new TokenResponse(tokenService.createToken(authentication), "Bearer"));
        }catch (AuthenticationException e){
            return ResponseEntity.badRequest().build();
        }
    }
    
    @PostMapping("/verify")
    public ResponseEntity<ValidTokenResponse> verify(@Valid @RequestBody ValidTokenRequest request) {
        boolean isValid = tokenService.isTokenValid(request.getToken());
        ValidTokenResponse vtr = new ValidTokenResponse(isValid);
        return ResponseEntity.ok().body(vtr);
    }
    
}