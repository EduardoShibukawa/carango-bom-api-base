package br.com.caelum.carangobom.dtos;

import br.com.caelum.carangobom.domain.User;

public class UserDetailResponse {
    private Long id;
    private String userName;

    public UserDetailResponse(Long id, String userName) {
        this.id = id;
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public static UserDetailResponse fromModel(User user) {
        return new UserDetailResponse(user.getId(), user.getUserName());
    }
}
