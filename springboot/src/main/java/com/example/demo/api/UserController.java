package com.example.demo.api;

import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * Created by L on 2017/8/21.
 */
@RestController
@RequestMapping("api/user/")
public class UserController {

    @RequestMapping(value = "register", method = RequestMethod.POST)
    public User register(@RequestBody User user) {
        user.setUsername("username: " + user.getUsername());
        user.setPassword("password: " + user.getPassword());
        return user;
    }

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public void login(@RequestBody String body) {
        System.out.println(body);
    }

    @RequestMapping(value = "getAllUserList", method = RequestMethod.POST)
    public String getAllUserList() {
        return "123";
    }
}

class User {
    private String username;
    private String password;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String toString() {
        return "User{" +
                "username='" + username + '\'' +
                ", password='" + password + '\'' +
                '}';
    }
}
