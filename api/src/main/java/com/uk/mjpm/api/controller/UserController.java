package com.uk.mjpm.api.controller;

import com.uk.mjpm.api.domain.User;
import com.uk.mjpm.api.domain.dto.UserResponseDto;
import com.uk.mjpm.api.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {


    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<?>createUser(@RequestBody User newUSer){
        User createUser = userService.createUser(newUSer);

        UserResponseDto response = UserResponseDto
                .builder()
                .status(HttpStatus.CREATED.value())
                .data(createUser)
                .build();

        return ResponseEntity.ok().body(response);
    }


}
