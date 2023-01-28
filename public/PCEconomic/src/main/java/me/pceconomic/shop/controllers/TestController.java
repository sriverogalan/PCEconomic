package me.pceconomic.shop.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    @Autowired
    private JavaMailSender javaMailSender;

    @GetMapping("/sendMail")
    public String sendMail() {
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom("miquelangel2309@gmail.com");
        message.setTo("hulkrojo2@gmail.com");
        message.setSubject("Testing");
        message.setText("This is for testing purposes");
        javaMailSender.send(message);
        return "Mail sent";
    }

}
