package br.com.caelum.carangobom.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import static javax.persistence.GenerationType.IDENTITY;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

@Entity
public class Brand {

    @Id @GeneratedValue(strategy = IDENTITY)
    private Long id;

    @NotBlank
    @Size(min = 2, message = "Deve ter {min} ou mais caracteres.")
    private String name;

    public Brand(Long id) {
    	this.id = id;
    }
    
    public Brand(String name) {
    	this.name = name;
    }
}
