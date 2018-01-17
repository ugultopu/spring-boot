package sample.web.secure.jdbc.entity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.springframework.security.core.GrantedAuthority;

import lombok.Getter;

@Entity
@Getter
public class Authority implements GrantedAuthority {

    @Id
    @GeneratedValue
    private Integer id;

    private String authority;

}
