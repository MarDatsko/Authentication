package com.example.authentication.controller;

import com.example.authentication.dto.UserDto;
import com.example.authentication.entity.User;
import com.example.authentication.entity.UserRole;
import com.example.authentication.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Log4j2
@RestController
public class UsersController {

    private final UserService userService;

    @Autowired
    public UsersController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> userDtoList = (userService.getAllUsers().stream()
                .map(UserDto::mapUserToUserDto)
                .collect(Collectors.toList()));
        return ResponseEntity.status(HttpStatus.OK)
                .body(userDtoList);
    }


    @GetMapping("/admin")
    public ResponseEntity<List<User>> getAllUsersForAdmin() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @PostMapping("/admin")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setLastName(userDto.getLastName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(UUID.randomUUID().toString());
        user.setCreateDate(new Date(System.currentTimeMillis()));
        user.setRole(UserRole.USER);

        userService.saveUser(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/admin/{id}")
    public HttpStatus deleteUser(@PathVariable(value = "id") Long id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            log.info("User not found in database");
        }
        return HttpStatus.OK;
    }

    @PutMapping("/admin/{id}")
    public ResponseEntity<User> updateUser(@PathVariable(value = "id") Long id, @RequestBody User user) throws Exception {
        userService.updateUser(id, user);
        return ResponseEntity.ok().body(user);
    }
}
