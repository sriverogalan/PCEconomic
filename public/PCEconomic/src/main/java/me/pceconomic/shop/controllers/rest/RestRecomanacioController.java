package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.ContadorArticle;
import me.pceconomic.shop.repositories.VisitaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
