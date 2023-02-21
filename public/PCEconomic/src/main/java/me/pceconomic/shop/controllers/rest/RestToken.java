package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestToken {

    @GetMapping("/token")
    public String generateToket(HttpServletRequest request) {
        return request.getSession().getAttribute("token").toString();
    }
}
