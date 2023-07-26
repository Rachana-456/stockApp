package stockApp.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LoginController {

    @GetMapping("/hello")
    public String hello(){

        return "<h3> Hi Team, This is first API for java POC</h3>";
    }
}
