package br.com.caelum.carangobom.domain;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class User {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private Long id;

    @NotBlank
    @Size(min=5)
    private String userName;

    @NotBlank
    @Size(min=5)
    private String password;
    
    public User(String userName, String password){
        this.userName = userName;
        this.password = password;
    }
    
}
