package stockApp.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import stockApp.model.User;
import stockApp.repository.UserRepository;

import java.util.ArrayList;
import java.util.Collection;

@Service @Slf4j
public class CustomUserDetailsServiceImpl implements UserDetailsService {
    @Autowired
    private UserRepository userRepository;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userRepository.findByUsername(username);

        if(user == null){
            log.error("User not Found in Database ");
            throw new UsernameNotFoundException("User not Found in Database ");
        }
        else {
            log.error("User Found in Database ");
        }

        Collection<SimpleGrantedAuthority> authorities = new ArrayList<>();
        user.getRoles().forEach(role -> authorities.add(new SimpleGrantedAuthority(role.getName())));
        String u = user.getUsername();
        String p = user.getPassword();
        return new org.springframework.security.core.userdetails.User(user.getUsername(),
                user.getPassword(),authorities);
    }
}
