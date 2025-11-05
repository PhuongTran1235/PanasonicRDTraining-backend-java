package com.training.articles.controller;

import com.training.articles.Common;
import com.training.articles.dto.user.CreateUserDto;
import com.training.articles.model.Users;
import com.training.articles.service.UserService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController("userController")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Common.ROOT_PATH + "/users/create")
    public Users createUser(@RequestBody CreateUserDto createUserDto) {
        return userService.createUser(createUserDto);
    }

}
