/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.stealth.rest.webservices.user;

import com.stealth.rest.webservices.user.exception.UserNotFoundException;
import java.net.URI;
import java.util.List;
import java.util.Optional;
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
public class UserJPAController {
    
//    @Autowired
//    private UserDoaService userDoaService;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private PostRepository postRepository;
    
    // url -> /users
    @GetMapping(path = "/jpa/users")
    public List<User> retrieveAllUsers()
    {
        //List<User> users = userDoaService.getAllUser();
        List<User> users = userRepository.findAll();
        if(users.isEmpty())
            throw new UserNotFoundException("No record found");
        return users;
    }
    
    //url -> /users/{id}
    @GetMapping(path = "/jpa/users/{id}")
    public Optional<User> retrieveUser(@PathVariable int id)
    {
        //User user = userDoaService.findUser(id);
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("User does not exist : " + id);
        return user;
    }
    
    //url -> /users
    @PostMapping(path = "/jpa/users")
    public ResponseEntity createUser(@Valid @RequestBody User user)
    {
//        User savedUser = userDoaService.addUser(user);
        User savedUser = userRepository.save(user);
        //after adding new user, get/generate the current url with the new id appended and display the result
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedUser.getId()).toUri();
        return ResponseEntity.created(location).build();        
    }
    
    //url -> /users/{id}
    @DeleteMapping(path = "jpa/users/{id}")
    public void deleteUser(@PathVariable int id)
    {
        //User user = userDoaService.deleteUser(id);
        userRepository.deleteById(id);
//        if(user == null)
//            throw new UserNotFoundException("User does not exist : " + id);
        
    }
    
    // url -> /jpa/users/{id}/posts
    @GetMapping(path = "/jpa/users/{id}/posts")
    public List<Post> retrieveAllPost(@PathVariable int id)
    {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("No record found");
        return user.get().getPosts();
    }
    
    @PostMapping(path = "/jpa/users/{id}/posts")
    public ResponseEntity createPost(@PathVariable int id, @RequestBody Post post)
    {
        Optional<User> user = userRepository.findById(id);
        if(!user.isPresent())
            throw new UserNotFoundException("No record found");
        User savedUser = user.get();
        post.setUser(savedUser);
        postRepository.save(post);
        //after adding new user, get/generate the current url with the new id appended and display the result
        URI location = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(post.getId()).toUri();
        return ResponseEntity.created(location).build();        
    }
}
