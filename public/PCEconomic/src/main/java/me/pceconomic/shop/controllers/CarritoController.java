package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.repositories.PropietatsRepository;
import me.pceconomic.shop.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CarritoController {

    private final CarritoService carritoService;

    @Autowired
    public CarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/addcarrito")
    public String addArticleToCart(Model model) {
        carritoService.addArticleToCart();
        return "redirect:/carrito";
    }

    @GetMapping("/carrito")
    public String carrito(Model model) {
        model.addAttribute("carrito", carritoService.getArticlesFromCart());
        return "carrito";
    }
}
