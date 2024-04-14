package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.LoginForm;
import com.example.technologiesieciowe.service.LoginService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.naming.AuthenticationException;
/**
 * Controller class for handling login-related HTTP requests.
 */
@RestController
public class LoginController {
    private final LoginService loginService;

    /**
     * Constructor injection for LoginController.
     * @param loginService The LoginService instance to be injected.
     */
    @Autowired
    public LoginController(LoginService loginService) {
        this.loginService = loginService;
    }

    /**
     * Endpoint for user login.
     * @param loginForm The LoginForm object containing user login credentials.
     * @return ResponseEntity containing a token if login is successful, or an error message if not.
     * @throws AuthenticationException If authentication fails.
     */
    @PostMapping("/login")
    @ResponseStatus(code = HttpStatus.ACCEPTED)
    public ResponseEntity<String> login(@RequestBody LoginForm loginForm) throws AuthenticationException {
        String token = loginService.userLogin(loginForm);
        if(token == null) {
            return new ResponseEntity<>("Wrong login or password", HttpStatus.UNAUTHORIZED);
        } else {
            return new ResponseEntity<>(token, HttpStatus.OK);
        }
    }
}