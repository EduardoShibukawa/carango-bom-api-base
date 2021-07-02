package br.com.caelum.carangobom.users.dtos;

import br.com.caelum.carangobom.users.entities.User;
import lombok.Value;

@Value
public class UserDetailResponse {
    
	Long id;
	
    String username;

    public static UserDetailResponse fromModel(User user) {
        return new UserDetailResponse(user.getId(), user.getUsername());
    }
}
