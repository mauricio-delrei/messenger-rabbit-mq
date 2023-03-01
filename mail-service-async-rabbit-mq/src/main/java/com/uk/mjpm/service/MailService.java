package com.uk.mjpm.service;

import org.springframework.stereotype.Service;

@Service
public interface MailService {
    void sendMail(String message, String subject, String to, String from);
}
