package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.ContadorArticle;
import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.domain.entities.article.Visita;
import me.pceconomic.shop.repositories.VisitaRepository;
import org.springframework.http.HttpRequest;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class RestRecomanacioController {
    private VisitaRepository visitaRepository;

    public RestRecomanacioController(VisitaRepository visitaRepository) {
        this.visitaRepository = visitaRepository;
    }

    @GetMapping("/api/recomanacio")
    public List<ContadorArticle> recomanacio(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) return null;

        List<ContadorArticle> contadorArticle = (List<ContadorArticle>) session.getAttribute("contadors");
        if (contadorArticle == null) return null;
        return contadorArticle;
    }

}
