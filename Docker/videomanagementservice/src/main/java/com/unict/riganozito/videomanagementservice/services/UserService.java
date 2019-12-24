package com.unict.riganozito.videomanagementservice.services;

import java.util.Optional;

import com.unict.riganozito.videomanagementservice.entity.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserRepository repository;

    public User addUser(User user) {
        return repository.save(user);
    }

    public User findUserByUsername(String username) {
        Optional<User> user = repository.findByUsername(username);
        if (user == null || !user.isPresent())
            return null;
        else
            return user.get();
    }
}
