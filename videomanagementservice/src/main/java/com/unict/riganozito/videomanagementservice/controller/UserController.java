package com.unict.riganozito.videomanagementservice.controller;

import com.unict.riganozito.videomanagementservice.entity.User;
import com.unict.riganozito.videomanagementservice.services.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping(path = "/register")
public class UserController {

    @Autowired
    UserService userService;

    @PostMapping(path = "/")
    public @ResponseBody User addUser(@RequestBody User user) {
        return userService.addUser(user);
    }
}