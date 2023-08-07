package stockApp.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringRunner;
import stockApp.model.User;
import stockApp.repository.UserRepository;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.when;

@RunWith(SpringRunner.class)
@SpringBootTest
public class CustomUserDetailsServiceImplUnitTest {

    @Autowired
    private CustomUserDetailsServiceImpl customUserDetailsService;
    @Mock
    private UserRepository userRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Test
    public void testLoadUserByUsername() {
        String username="deepak@gmail.com";
        User user = new User();
        user.setPassword(passwordEncoder.encode("12345678"));

        assertEquals("deepak@gmail.com",customUserDetailsService.loadUserByUsername(username).getUsername());

        //when(userRepository.findByUsername(username)).thenReturn(user);
        //User user1 = userRepository.findByUsername(username);
       //assertEquals(passwordEncoder.encode("12345678"),customUserDetailsService.loadUserByUsername(username).getPassword());
    }
}
