package com.uk.mjpm.api.service;

import com.uk.mjpm.api.domain.User;
import com.uk.mjpm.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    @Autowired
    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public User createUser(User newUser) {
        User savedUser = userRepository.save(newUser);
        return savedUser;
    }
}
