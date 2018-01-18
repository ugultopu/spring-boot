package sample.web.secure.jdbc;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import sample.web.secure.jdbc.entity.User;
import sample.web.secure.jdbc.repository.UserRepository;

@Component
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User user = userRepository.findUserDetailByUsername(username);

        if (user == null) {
            throw new UsernameNotFoundException(
                "User with username '" + username + "' is not found."
            );
        } else {
            return user;
        }

    }

}
