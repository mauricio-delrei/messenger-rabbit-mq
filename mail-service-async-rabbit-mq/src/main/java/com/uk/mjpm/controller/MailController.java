package com.uk.mjpm.controller;

import com.uk.mjpm.config.UserMessageQueueConfig;
import com.uk.mjpm.domain.UserMailQueueMessage;
import com.uk.mjpm.service.MailService;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/mail")
public class MailController {

    private final MailService mailService;

    @Value("${authentication.username}")
    private String FROM;


    @Autowired
    public MailController(MailService mailService) {
        this.mailService = mailService;
    }

    @RabbitListener(queues = UserMessageQueueConfig.USER_QUEUE_NAME)
    public void listener(UserMailQueueMessage queueMessage){
        System.out.println(queueMessage);

        StringBuilder message = new StringBuilder();
        message.append("Hi ");
        message.append(queueMessage.getUserMessage().getFirstName());
        message.append(", Your new account is created Successfully");
        String subject = "New Account Created";

        mailService.sendMail(
                message.toString(),
                subject,
                queueMessage.getUserMessage().getEmail(),
                FROM
        );

    }
}
