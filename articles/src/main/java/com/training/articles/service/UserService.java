package com.training.articles.service;

import com.training.articles.dao.UserRepository;
import com.training.articles.dto.user.CreateUserDto;
import com.training.articles.dto.SearchDto;
import com.training.articles.dto.user.UpdateUserDto;
import com.training.articles.model.Users;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    private final UserRepository userRepository;
    private Object dto;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public Users createUser(CreateUserDto user) {
        Users newUser = new Users();
        newUser.setName(user.getName());
        newUser.setEmail(user.getEmail());
        userRepository.save(newUser);
        return  newUser;
    }


    public Users updateUser(UpdateUserDto updateUserDto, Long userId) {
        Users user = userRepository.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));

        if (updateUserDto.getName() != null) user.setName(updateUserDto.getName());
        if (updateUserDto.getEmail() != null) user.setEmail(updateUserDto.getEmail());

        return userRepository.save(user);
    }

    public void deleteUser(Long id) {
        userRepository.deleteById(id);
    }

    public Page<Users> queryListUser(SearchDto searchUserDto) {
        return userRepository.searchByNameOrEmail(searchUserDto.getSearch(), searchUserDto.toPageable());
    }

}
