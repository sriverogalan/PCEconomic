package me.pceconomic.shop.controllers;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.services.CreationService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class FrontController {

    private final CreationService creationService;
    private final FrontService frontService;

    @Autowired
    public FrontController(FrontService frontService, CreationService creationService) {
        this.creationService = creationService;
        this.frontService = frontService;
    }

    @GetMapping("/")
    public String index(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "index";

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");

        return "index";
    }

    @GetMapping("/article/{idArticle}/{idPropietat}")
    public String article(Model model, @PathVariable int idArticle, @PathVariable int idPropietat, HttpServletRequest request) {
        frontService.article(model, idArticle, idPropietat, request);
        return "article";
    }

    @GetMapping("/categoria/{id}")
    public String getCategories(Model model, @PathVariable int id, HttpServletRequest request) {
        frontService.getCategoria(model, id, request);
        return "categoria";
    }

    @GetMapping("/carrito/direccion")
    public String getDireccion(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession();
        if (session == null) return "index";

        Client client = (Client) session.getAttribute("persona");
        model.addAttribute("client", client == null ? "LOGIN" : "LOGOUT");
        return "direcciones-envio";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        creationService.create();
        return "redirect:/";
    }

    @GetMapping("/crear1000")
    public String create1000() {
        creationService.crear1000();
        return "redirect:/";
    }

}
