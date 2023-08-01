package stockApp.controller;

import com.fasterxml.jackson.core.JsonEncoding;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.bson.json.JsonObject;
import org.bson.json.JsonWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.*;
import stockApp.filter.CustomAuthorizationFilter;
import stockApp.model.User;
import stockApp.repository.UserRepository;
import stockApp.service.UserService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    UserRepository userRepository;
    @Autowired
    UserService userService;
    @Autowired
    CustomAuthorizationFilter customAuthorizationFilter;
    @GetMapping("/hello")
    public String hello(){
        System.out.println("dummy : Private Api");
//        return " Hi There! You have Passed Valid Token So Now " +
//                "You are accessing A Authorized API/Resources  : localhost:8080/api/hello";
        return "true";
    }
    @GetMapping("/validateToken")
    public String validateToken(@RequestHeader("Authorization") String tokenHeader){
        System.out.println("called : " + tokenHeader);
        boolean tokenIsValid = customAuthorizationFilter.validateToken(tokenHeader);
        //System.out.println("tokenIsValid : " + tokenIsValid);
        return "true";
    }









//    @GetMapping("/validateToken")
//    public String validateToken(@RequestParam("authorizationHeader") String tokenHeader){
//        //System.out.println("called : " + tokenHeader);
//        boolean tokenIsValid = customAuthorizationFilter.validateToken(tokenHeader);
//        //System.out.println("tokenIsValid : " + tokenIsValid);
//        return "true";
//    }

    @PostMapping("/registeruser")
    public User registerUser(@RequestBody User user){
        User myuser =userService.registerUser(user);
        return myuser;
    }

}
