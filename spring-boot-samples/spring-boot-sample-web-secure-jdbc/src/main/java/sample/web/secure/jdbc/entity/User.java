package sample.web.secure.jdbc.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToMany;

import org.springframework.security.core.userdetails.UserDetails;

import lombok.Getter;

@Entity
@Getter
public class User implements UserDetails {

    @Id
    @GeneratedValue
    private Integer id;

    @Column(unique = true)
    private String username;

    private String password;

    /*
     * FIXME
     * fetch = FetchType.EAGER is a workaround. Find the real reason for the
     * following error and fix it:
     *
     *     org.hibernate.LazyInitializationException: failed to lazily
     *     initialize a collection of role:
     *     sample.web.secure.jdbc.entity.User.authorities,
     *     could not initialize proxy - no Session
     */
    @ManyToMany(fetch = FetchType.EAGER)
    private Set<Authority> authorities;

    private boolean accountNonExpired;

    private boolean accountNonLocked;

    private boolean credentialsNonExpired;

    private boolean enabled;

}
