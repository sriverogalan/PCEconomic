package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class CarritoController {

    private final HttpSession session;
    private final FrontService frontService;

    @Autowired
    public CarritoController(HttpSession session, FrontService frontService) {
        this.session = session;
        this.frontService = frontService;
    }

    @GetMapping("/addcarrito/{id}")
    public String addArticleToCart(Model model, @PathVariable int id) {
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String carrito(Model model) {
        frontService.sendListsToView(model);
        return "carrito";
    }
}
