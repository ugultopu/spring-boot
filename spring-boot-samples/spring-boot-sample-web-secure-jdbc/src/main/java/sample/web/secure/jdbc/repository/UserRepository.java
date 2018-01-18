package sample.web.secure.jdbc.repository;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import sample.web.secure.jdbc.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    /**
     * To be used by {@link sample.web.secure.jdbc.JpaUserDetailsService#loadUserByUsername}
     */
    @EntityGraph(attributePaths = "authorities")
    User findUserDetailByUsername(String username);

}
