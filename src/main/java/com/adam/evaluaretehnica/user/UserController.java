package com.adam.evaluaretehnica.user;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    //only get username
    @GetMapping
    public List<User> getUsersForQuests() {
        return null;
    }
}
