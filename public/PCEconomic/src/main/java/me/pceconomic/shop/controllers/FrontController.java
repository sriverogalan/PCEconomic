package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.*;
import me.pceconomic.shop.services.CreationService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.Set;

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
        return "index";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        creationService.create();
        return "redirect:/";
    }

    @GetMapping("/areaclients")
    public String areaClients() {
        return "areaclients";
    }

}
