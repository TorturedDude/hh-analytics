package com.example.restapi.services;

import com.example.restapi.dto.user.EditUserDto;
import com.example.restapi.dto.user.GetUserDto;
import com.example.restapi.dto.user.UserCreateDto;
import com.example.restapi.exceptions.ResourceNotFoundException;
import com.example.restapi.exceptions.ValueAlreadyExistsException;
import com.example.restapi.mappers.UserMapper;
import com.example.restapi.models.User;
import com.example.restapi.repositories.UserRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.relational.core.conversion.DbActionExecutionException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;

@Service
@AllArgsConstructor
@Slf4j
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;

    public void createUser(UserCreateDto userCreateDto) {
        try {

            User user = userMapper.toEntity(userCreateDto);
            user.setPassword(passwordEncoder.encode(userCreateDto.getPassword()));

            log.debug("Saving user: " + user);
            userRepository.save(user);

        } catch (DbActionExecutionException e) {
            log.debug("User exist");
            throw new ValueAlreadyExistsException("Username already exists");
        }
    }

    public User getByUsername(String username) {
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

    public GetUserDto update(EditUserDto editUserDto, int userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));

        user.setTelegramChatId(editUserDto.getTelegramChatId());

        return userMapper.toGetUserDto(userRepository.save(user));
    }

    public GetUserDto getById(int userId) {
        return userMapper.toGetUserDto(userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"))
        );
    }

    public List<GetUserDto> getUsersByQuery(String query) {
        List<User> users = userRepository.findByQuery(query);
        if (users == null || users.isEmpty()) {
            return Collections.emptyList();
        }

        return userMapper.toGetUserDto(users);
    }
}
