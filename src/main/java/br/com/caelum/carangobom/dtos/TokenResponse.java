package br.com.caelum.carangobom.dtos;

import lombok.Value;

@Value
public class TokenResponse {
    String token;
    String type;
}
