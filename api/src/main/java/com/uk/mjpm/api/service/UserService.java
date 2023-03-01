package com.uk.mjpm.api.service;

import com.uk.mjpm.api.domain.User;
import org.springframework.stereotype.Service;

@Service
public interface UserService {

    User createUser(User newUser);
}
