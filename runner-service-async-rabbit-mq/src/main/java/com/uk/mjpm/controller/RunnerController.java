package com.uk.mjpm.controller;

import com.uk.mjpm.config.UserMessageQueueConfig;
import com.uk.mjpm.domain.User;
import com.uk.mjpm.domain.UserMailQueueMessage;
import com.uk.mjpm.domain.dto.RunnerResponseDto;
import com.uk.mjpm.domain.dto.UserResponseDto;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Date;
import java.util.UUID;

@RestController
@RequestMapping("/simulate/users")
public class RunnerController {



    private final RestTemplate restTemplate;


    private final RabbitTemplate rabbitTemplate;

    @Autowired
    public RunnerController(RestTemplate restTemplate, RabbitTemplate rabbitTemplate) {
        this.restTemplate = restTemplate;
        this.rabbitTemplate = rabbitTemplate;
    }


    @Value("${url.user-service}")
    private String userServiceUrl;

    @Value("${url.mail-service}")
    private String mailServiceUrl;

    @PostMapping
    public ResponseEntity<?> createUser(@RequestBody User newUser){
        ResponseEntity<UserResponseDto> response = null;
        try {

            response = restTemplate.exchange(userServiceUrl, HttpMethod.POST,new HttpEntity<>(newUser), UserResponseDto.class);

            if(response.getBody().getStatus() != HttpStatus.CREATED.value())
            {
                throw new Exception("Error while creating user");
            }

            User createdUser = response.getBody().getData();

            RunnerResponseDto responseDto = RunnerResponseDto
                    .builder()
                    .user(createdUser)
                    .message("User created successfully. Mail will be sent shortly")
                    .build();


            UserMailQueueMessage userMailQueueMessage =
                    UserMailQueueMessage.builder()
                            .mailQueueMessageId(UUID.randomUUID().toString())
                            .queueMessageDate(new Date())
                            .userMessage(newUser)
                            .build();

            rabbitTemplate.convertAndSend(
                    UserMessageQueueConfig.EXCHANGE_NAME,
                    UserMessageQueueConfig.ROUTING_KEY,
                    userMailQueueMessage
            );

            return ResponseEntity.status(HttpStatus.OK).body(responseDto);
        }catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(e.getMessage());
        }
    }
}



