package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTokenController {

    @GetMapping("/token")
    public String getToken(HttpServletRequest request) {
        return request.getSession().getAttribute("token").toString();
    }
}
