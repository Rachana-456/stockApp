package stockApp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import stockApp.model.Role;
import stockApp.model.User;
import stockApp.repository.UserRepository;

import javax.validation.ConstraintViolationException;
import java.util.Collection;
import java.util.Collections;

import static junit.framework.TestCase.assertNotNull;
import static org.junit.Assert.*;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserServiceImplUnitTest {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;

    @Test
    public void testRegisterUser() {
        User user = new User();
        user.setId("102");
        user.setFirstname("Nayeem");
        user.setLastname("Bhati");
        user.setUsername("nayeem@gmail.com");
        user.setPassword("12345678");
        user.setRoles(Collections.singleton(new Role(null, "ROLE_USER")));

        assertNotNull(user);
        assertEquals("Nayeem",userServiceImpl.registerUser(user).getFirstname());
        assertEquals("nayeem@gmail.com",userServiceImpl.registerUser(user).getUsername());
        assertEquals("102",userServiceImpl.registerUser(user).getId());

        // Using Mockito

        when(userService.registerUser(user)).thenReturn(user);
        User user1 = userService.registerUser(user);
        assertNotNull(user1);
        assertEquals("102",user1.getId() );
    }


    @Test
    public void testFindByUsername(){
        User user = new User();
        user.setId("1000");
        user.setFirstname("Deepak");
        user.setLastname("Sharma");
        user.setUsername("deepak@gmail.com");
        user.setPassword("12345678");
        when(userRepository.findByUsername("deepak@gmail.com")).thenReturn(user);

        User user1 = userRepository.findByUsername("deepak@gmail.com");
        assertEquals("deepak@gmail.com",user1.getUsername() );

    }
}
