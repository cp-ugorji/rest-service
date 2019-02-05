/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.rest.webservices.user;

import com.stealth.rest.webservices.user.exception.UserNotFoundException;
import java.net.URI;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 *
 * @author Chux
 */
@RestController
public class UserController {
    
    @Autowired
    private UserDoaService userDoaService;
    
    // url -> /users
    @GetMapping(path = "/users")
    public List<User> retrieveAllUsers()
    {
        List<User> users = userDoaService.getAllUser();
        if(users.isEmpty())
            throw new UserNotFoundException("No record found");
        return users;
    }
    
    //url -> /users/{id}
    @GetMapping(path = "/users/{id}")
    public User retrieveUser(@PathVariable int id)
    {
        User user = userDoaService.findUser(id);
        if(user == null)
            throw new UserNotFoundException("User does not exist : " + id);
        return user;
    }
    
    //url -> /users
    @PostMapping(path = "/users")
    public ResponseEntity createUser(@Valid @RequestBody User user)
    {
        User savedUser = userDoaService.addUser(user);
        //after adding new user, get/generate the current url with the new id appended and display the result
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();        
    }
    
    //url -> /users/{id}
    @DeleteMapping(path = "/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        User user = userDoaService.deleteUser(id);
        if(user == null)
            throw new UserNotFoundException("User does not exist : " + id);
        
    }
}
