package com.example.authentication.dao;

import com.example.authentication.entity.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User getUserByUserName (String userName);
}
