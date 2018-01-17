package sample.web.secure.jdbc.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import sample.web.secure.jdbc.entity.User;

public interface UserRepository extends JpaRepository<User, Integer> {

    User findByUsername(String username);

}
