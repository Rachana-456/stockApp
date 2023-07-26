package stockApp.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.CustomScopeConfigurer;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import stockApp.model.Role;
import stockApp.model.User;
import stockApp.repository.RoleRepository;
import stockApp.repository.UserRepository;

import java.sql.ClientInfoStatus;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

@Service@RequiredArgsConstructor@Slf4j@Transactional
public class UserServiceImpl implements UserService{
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Override
    public User registerUser(User user) {
        List<Role> list = new ArrayList<>();
        list.add(new Role(null,"ROLE_USER"));
        user.setRoles(list);

        user.setPassword(passwordEncoder.encode(user.getPassword()));
        log.info("Saving New User = "+ user.getFirstname()+" "+user.getLastname()+" into Database");
        return userRepository.save(user);
    }
}
