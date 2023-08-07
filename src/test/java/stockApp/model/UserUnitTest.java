package stockApp.model;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.validation.ConstraintViolationException;
import java.util.Collections;

import static org.junit.Assert.*;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UserUnitTest {

    @Test
    public void testGetId(){
        User user = new User();
        user.setId("1003");
        user.setFirstname("Nayeem");
        user.setLastname("Bhati");
        user.setUsername("nayeem@gmail.com");
        user.setPassword("12345678");
        user.setRoles(Collections.singleton(new Role(null, "ROLE_USER")));
        assertEquals("1003",user.getId());
        assertEquals("Nayeem",user.getFirstname());
        assertEquals("Bhati",user.getLastname());
        assertEquals("nayeem@gmail.com",user.getUsername());
        assertEquals("12345678",user.getPassword());
    }


}
