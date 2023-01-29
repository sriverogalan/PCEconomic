package me.pceconomic.shop.controllers;

import me.pceconomic.shop.services.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class TestController {

    private final MailService mailService;

    @Autowired
    public TestController(MailService mailService) {
        this.mailService = mailService;
    }

    @GetMapping("/sendMail")
    public String sendMail() {
        mailService.sendMail("hulkrojo2@gmail.com", "Test", "Test");
        return "Mail sent";
    }

}
