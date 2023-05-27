package com.tipu.crudbackend.controller;

import com.tipu.crudbackend.exception.ResourceNotFoundException;
import com.tipu.crudbackend.model.User;
import com.tipu.crudbackend.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("/api/")
public class UserController {

    @Autowired
    UserRepository userRepo;

    @Autowired


    @GetMapping("/users")
    public List<User> getAllUser(){
        return userRepo.findAll();
    }

    @PostMapping("/user")
    public User addUser(@RequestBody User user){

        return userRepo.save(user);
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not exist with id :" + id));

        return ResponseEntity.ok(user);
    }

    @PutMapping("/user/{id}")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User newUser){
        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not exist with id :" + id));

        user.setFirstName(newUser.getFirstName());
        user.setLastName(newUser.getLastName());
        user.setEmailId(newUser.getEmailId());

        User updatedUser = userRepo.save(user);
        return ResponseEntity.ok(updatedUser);
    }

    @DeleteMapping("/user/{id}")
    public ResponseEntity <Map< String, Boolean >> deleteUser(@PathVariable Long id){

        User user = userRepo.findById(id)
                .orElseThrow(()-> new ResourceNotFoundException("User not exist with id :" + id));
        userRepo.delete(user);

        Map < String, Boolean > response = new HashMap< >();
        response.put("deleted", Boolean.TRUE);
        return ResponseEntity.ok(response);

    }





}
