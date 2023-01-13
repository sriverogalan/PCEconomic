package me.pceconomic.shop.controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;

public class IssueController implements ErrorController {

    @RequestMapping("/error")
    public String handleError() {
        return "error";
    }
}
