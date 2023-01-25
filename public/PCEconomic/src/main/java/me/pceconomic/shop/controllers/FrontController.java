package me.pceconomic.shop.controllers;

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
    public String index(Model model) {
        frontService.index(model);
        return "index";
    }

    @GetMapping("/article/{idArticle}/{idPropietat}")
    public String article(Model model, @PathVariable int idArticle, @PathVariable int idPropietat) {
        frontService.article(model, idArticle, idPropietat);
        return "article";
    }

    @GetMapping("/categoria/{id}")
    public String getCategories(Model model, @PathVariable int id) {
        frontService.getCategoria(model, id);
        return "categoria";
    }

    @GetMapping("/carrito/direccion")
    public String getDireccion(Model model) {
        frontService.getDireccion(model);
        return "direcciones-envio";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        creationService.create();
        return "redirect:/";
    }

/*    @GetMapping("/register")
    public String register(Model model) {
        frontService.sendListsToView(model);
        return "register";
    }*/

}
