package com.uk.mjpm.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

@Service
public class MailServiceImpl implements MailService{

    @Value("${authentication.username}")
    private String AUTHENTICATION_USERNAME;

    @Value("${authentication.password}")
    private String AUTHENTICATION_PASSWORD;





    @Override
    public void sendMail(String message, String subject, String to, String from) {

        //Variable for gmail
        String host="smtp.gmail.com";
        Properties properties = System.getProperties();
        System.out.println("PROPERTIES " + properties);

        //setting important information to properties object

        //host set
        properties.put("mail.smtp.host", host);
        properties.put("mail.smtp.port","465");
        properties.put("mail.smtp.ssl.enable","true");
        properties.put("mail.smtp.auth","true");


        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(AUTHENTICATION_USERNAME,AUTHENTICATION_PASSWORD);
            }
        });

        session.setDebug(true);


        MimeMessage mimeMessage = new MimeMessage(session);

            try{
                mimeMessage.setFrom(from);

                mimeMessage.addRecipient(Message.RecipientType.TO, new InternetAddress(to));

                mimeMessage.setSubject(subject);

                mimeMessage.setText(message);

                Transport.send(mimeMessage);

                System.out.println("Sent success...................");

            } catch (Exception e) {
                e.printStackTrace();
            }

    }
}
