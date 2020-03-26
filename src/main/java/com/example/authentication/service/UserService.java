package com.example.authentication.service;

import com.example.authentication.dao.UserRepository;
import com.example.authentication.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<User> getAllUsers() {
        List<User> usersList = new ArrayList<>();
        userRepository.findAll().forEach(usersList::add);
        return usersList;
    }

    public User getById(Long id) {
        return userRepository.findById(id).orElse(null);
    }

    public User saveUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(Long id, User user) throws Exception {
        User userFromDb = userRepository.findById(id).orElse(null);
        if (userFromDb == null){
            throw new Exception();
        }
        userFromDb.setUserName(user.getUserName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setLastName(user.getLastName());
        userFromDb.setEmail(user.getEmail());

        return userRepository.save(userFromDb);
    }

    public void delete(Long id) {
        userRepository.deleteById(id);
    }

    public User getUserByUserName(String userName) {
        return userRepository.getUserByUserName(userName);
    }
}
