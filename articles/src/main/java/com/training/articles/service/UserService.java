package com.training.articles.service;

import com.training.articles.dto.user.CreateUserDto;
import com.training.articles.dto.user.GetUsersResponseDto;
import com.training.articles.dto.user.UpdateUserDto;
import org.springframework.stereotype.Service;
import org.springframework.beans.factory.annotation.Value;

import java.util.List;

@Service
public class UserService {
    private final KeycloakCallApiService keycloakCallApiService;

    @Value("${keycloack.realm-code}")
    private String realmCode;

    public UserService( KeycloakCallApiService keycloakCallApiService) {
        this.keycloakCallApiService = keycloakCallApiService;
    }

    public void createUser(CreateUserDto user) {
        keycloakCallApiService.createUser(realmCode, user.getName(), user.getEmail());
    }


    public void updateUser(UpdateUserDto updateUserDto, String userId) {
        keycloakCallApiService.updateUser(realmCode, userId, updateUserDto.getName(), updateUserDto.getEmail());
    }

    public void deleteUser(String userId) {

        keycloakCallApiService.deleteUser(realmCode, userId);
    }

    public List<GetUsersResponseDto> getListUsers(String realmCode) {
        return keycloakCallApiService.getListUser(realmCode);
    }

}
