package sample.web.secure.jdbc;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import sample.web.secure.jdbc.entity.User;
import sample.web.secure.jdbc.repository.UserRepository;

@Component
@Transactional
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        /*
         * FIXME
         * Throw UsernameNotFoundException when user is not found with the
         * given username.
         */
        /*
         * FIXME
         * This is a workaround, not a (real) solution.
         */
        User user = userRepository.findByUsername(username);
        System.out.println(user.getAuthorities());
        return user;
    }

}
