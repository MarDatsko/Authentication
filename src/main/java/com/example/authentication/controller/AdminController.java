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

@Log4j2
@RestController
@RequestMapping("/admin")
public class AdminController {

    private final UserService userService;

    @Autowired
    public AdminController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.status(HttpStatus.OK)
                .body(userService.getAllUsers());
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody UserDto userDto) {
        User user = new User();
        user.setUserName(userDto.getUserName());
        user.setLastName(userDto.getLastName());
        user.setLastName(userDto.getLastName());
        user.setEmail(userDto.getEmail());
        user.setPassword(UUID.randomUUID().toString());
        user.setCreateDate(new Date(System.currentTimeMillis()));
        user.setRole(UserRole.USER);

        userService.saveOrUpdate(user);

        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    @DeleteMapping("/delete/{id}")
    public HttpStatus deleteUser(@PathVariable(value = "id") Long id) {
        try {
            userService.delete(id);
        } catch (Exception e) {
            log.info("User not found in database");
        }
        return HttpStatus.OK;
    }

    @PutMapping("/update")
    public ResponseEntity<User> updateUser(@RequestBody User user) {
        userService.saveOrUpdate(user);
        return ResponseEntity.ok().body(user);
    }
}
