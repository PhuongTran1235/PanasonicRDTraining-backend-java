package com.training.articles.controller;

import com.training.articles.Common;
import com.training.articles.dto.user.CreateUserDto;
import com.training.articles.dto.user.GetUsersResponseDto;
import com.training.articles.dto.user.UpdateUserDto;
import com.training.articles.service.UserService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController("userController")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping(Common.ROOT_PATH + "/users/create")
    public void createUser(@RequestBody CreateUserDto createUserDto) {
         userService.createUser(createUserDto);
    }


    @GetMapping(Common.ROOT_PATH + "/users/create/{realm_code}")
    @PreAuthorize("hasRole('admin')")
    public List<GetUsersResponseDto> getListUser(
        @PathVariable(name = "realm_code") String realmCode
    ){
        return userService.getListUsers(realmCode);
    }

    @PutMapping(Common.ROOT_PATH + "/update/{userId}")
    @PreAuthorize("hasRole('user') and @articleSecurity.checkAuthor(#userId)")
    public void updateUser(@PathVariable(name = "userId") String userId, @RequestBody UpdateUserDto updateUserDto) {
        userService.updateUser(updateUserDto, userId);
    }

    @DeleteMapping(Common.ROOT_PATH + "delete/{userId}")
    @PreAuthorize("hasRole('admin') or (hasRole('user') and @articleSecurity.checkAuthor(#userId))")
    public void deleteUser(@PathVariable(name = "userId") String userId) {
        userService.deleteUser(userId);
    }

}
