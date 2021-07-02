package br.com.caelum.carangobom.auth.dtos;

import lombok.Value;

@Value
public class TokenResponse {
    String token;
    String type;
}
