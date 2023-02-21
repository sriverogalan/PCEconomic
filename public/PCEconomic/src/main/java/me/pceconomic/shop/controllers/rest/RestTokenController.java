package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTokenController {

    @GetMapping("/token")
    public String getToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) return "No Session Found";

        String token = (String) session.getAttribute("token");
        if (token == null) return "No Token Found";

        return token;
    }
}
