package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.categoria.Categoria;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class RestFrontController {
    private final ImatgeRepository imatgeRepository;
    private final CategoriaRepository categoriaRepository;
    private final ArticleRepository articleRepository;
    private final PropietatRepository propietatRepository;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public RestFrontController(PropietatsRepository propietatsRepository, PropietatRepository propietatRepository, ArticleRepository articleRepository, ImatgeRepository imatgeRepository, CategoriaRepository categoriaRepository) {
        this.imatgeRepository = imatgeRepository;
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.propietatsRepository = propietatsRepository;
        this.propietatRepository = propietatRepository;
    }

    @GetMapping("/api/articles")
    public List<Article> articleRepository() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/images")
    public List<Imatge> imatgeRepository() {
        return imatgeRepository.findAll();
    }

    @GetMapping("/api/propietats")
    public List<Propietats> propietatsRepository() {
        return propietatsRepository.findAll();
    }

    @GetMapping("/api/categories")
    public List<Categoria> categoriaRepository() {
        return categoriaRepository.findAll();
    }

    @GetMapping("/api/direccions")
    public Set<Direccio> direccions(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) return null;
        return (Set<Direccio>) session.getAttribute("direccions");
    }

    @GetMapping("/api/propietat")
    public List<Propietat> propietat() {
        return propietatRepository.findAll();
    }

    @GetMapping("/api/pedidos")
    public Set<Factura> pedidos(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) return null;
        Client client = (Client) session.getAttribute("persona");
        return client.getFactures();
    }
}
