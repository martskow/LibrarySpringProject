package com.example.technologiesieciowe.controllers;

import com.example.technologiesieciowe.infrastructure.entity.ReviewEntity;
import com.example.technologiesieciowe.infrastructure.entity.UserEntity;
import com.example.technologiesieciowe.service.UserService;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Map;

/**
 * Controller class for handling user-related HTTP requests.
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private final UserService userService;
    private final PasswordEncoder passwordEncoder;

    /**
     * Constructor injection for UserController.
     * @param userService The UserService instance to be injected.
     * @param passwordEncoder The PasswordEncoder instance to be injected.
     */
    @Autowired
    public UserController(UserService userService, PasswordEncoder passwordEncoder){
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    /**
     * Endpoint for adding a new user.
     * @param user The UserEntity object representing the new user.
     * @return The added UserEntity object.
     */
    @PostMapping("/add")
    @ResponseStatus(code = HttpStatus.CREATED)
    public UserEntity addUser(@RequestBody UserEntity user){
        return userService.addUser(user);
    }

    /**
     * Endpoint for deleting a user by ID.
     * @param id The ID of the user to delete.
     */
    @DeleteMapping("/delete/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void delete(@PathVariable Integer id) {
        userService.delete(id);
    }

    /**
     * Endpoint for retrieving all users.
     * @return Iterable collection of all UserEntity objects.
     */
    @GetMapping("/getAll")
    @ResponseStatus(code = HttpStatus.OK)
    public @ResponseBody Iterable<UserEntity> getAllBooks(){
        return userService.getAll();
    }

    /**
     * Endpoint for retrieving a single user by ID.
     * @param id The ID of the user to retrieve.
     * @return The UserEntity object with the specified ID.
     */
    @GetMapping("/getOne/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserEntity getOne (@PathVariable Integer id) {
        return userService.getOne(id);
    }

    /**
     * Endpoint for editing an existing user.
     * @param id The ID of the user to edit.
     * @param editedUser The UserEntity object containing the updated user information.
     * @return The updated UserEntity object.
     */
    @PutMapping("/edit/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserEntity editUser(@PathVariable Integer id, @RequestBody UserEntity editedUser) {
        return userService.editUser(id, editedUser);
    }

    /**
     * Endpoint for setting a new password for a user.
     * @param id The ID of the user to set the new password for.
     * @param user The UserEntity object containing the new password.
     * @return The updated UserEntity object with the new password.
     */
    @PutMapping("/newPassword/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public UserEntity editPassword(@PathVariable Integer id, @RequestBody UserEntity user) {
        return userService.newUserPassword(id, user);
    }

    @GetMapping("/getUserRole")
    @ResponseStatus(code = HttpStatus.OK)
    public String getUserRole() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
            if (authentication != null && authentication.isAuthenticated()) {
                Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
                if (!authorities.isEmpty()) {
                    return authorities.iterator().next().getAuthority();
                }
            }
            return null;
    }

    @GetMapping("/getUserId")
    @ResponseStatus(code = HttpStatus.OK)
    public String getUserId() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication != null && authentication.isAuthenticated()) {
            return authentication.getName().toString();
        }
        return null;
    }

    @GetMapping("/getUserStats/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public Map<String, Integer> userStats(@PathVariable Integer id) {
        return userService.userStats(id);
    }
}