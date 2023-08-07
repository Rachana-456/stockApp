package stockApp.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonObject;
import org.bson.json.JsonWriter;
import org.hibernate.validator.internal.constraintvalidators.bv.EmailValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.web.csrf.InvalidCsrfTokenException;
import org.springframework.security.web.csrf.MissingCsrfTokenException;
import org.springframework.security.web.server.csrf.CsrfToken;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import stockApp.filter.CustomAuthorizationFilter;
import stockApp.model.User;
import stockApp.repository.UserRepository;
import stockApp.service.UserService;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@Validated
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CustomAuthorizationFilter customAuthorizationFilter;

    @GetMapping("/validateToken")
    public boolean validateToken(@RequestHeader("Authorization") String tokenHeader) throws Exception {
        boolean tokenIsValid=false;
            try {
                tokenIsValid = customAuthorizationFilter.validateToken(tokenHeader);
            }catch (Exception exception){
                System.out.println("Invalid Token ");

            }
            return tokenIsValid;
    }

    @PostMapping("/user/register")
    public ResponseEntity<?> registerUser(@Valid @RequestBody User user){

        User myuser =userService.registerUser(user);
        return ResponseEntity.ok(myuser);
    }

}
