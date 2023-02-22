package me.pceconomic.shop.controllers.rest;

import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.domain.entities.article.Valoracions;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.repositories.ArticleRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import me.pceconomic.shop.repositories.ValoracionsRepository;
import me.pceconomic.shop.services.CarritoService;
import me.pceconomic.shop.services.FrontService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestCarritoController {

    private final CarritoService carritoService;
    private final FrontService frontService;
    private final ArticleRepository articleRepository;
    private final ValoracionsRepository valoracionsRepository;
    private final PersonaRepository personaRepository;

    @Autowired
    public RestCarritoController(CarritoService carritoService, FrontService frontService, ArticleRepository articleRepository, ValoracionsRepository valoracionsRepository, PersonaRepository personaRepository) {
        this.carritoService = carritoService;
        this.frontService = frontService;
        this.articleRepository = articleRepository;
        this.valoracionsRepository = valoracionsRepository;
        this.personaRepository = personaRepository;
    }

    @GetMapping("/api/carrito")
    public ShoppingCart getCarrito() {
        return carritoService.getCarrito();
    }

    @GetMapping("/api/valoracions")
    public List<Valoracions> valoracions() {
        return valoracionsRepository.findAll();
    }

    @GetMapping("/api/persones")
    public List<Persona> persones() {
        return personaRepository.findAll();
    }
}


