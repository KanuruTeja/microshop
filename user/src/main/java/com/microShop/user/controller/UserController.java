package com.microShop.user.controller;

import com.microShop.user.entity.User;
import com.microShop.user.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;


@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/register")
    public User registerUser(@RequestBody User user) {

        return userService.registerUser(user);
    }

    @GetMapping("/{email}")
    public Optional<User> getUser(@PathVariable String email) {
        return userService.getUserByEmail(email);
    }
}
