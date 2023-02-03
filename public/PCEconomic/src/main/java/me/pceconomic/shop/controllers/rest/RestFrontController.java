package me.pceconomic.shop.controllers.rest;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.categoria.Categoria;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.repositories.ArticleRepository;
import me.pceconomic.shop.repositories.CategoriaRepository;
import me.pceconomic.shop.repositories.ImatgeRepository;
import me.pceconomic.shop.repositories.PropietatsRepository;
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

    private final PropietatsRepository propietatsRepository;

    @Autowired
    public RestFrontController(PropietatsRepository propietatsRepository, ArticleRepository articleRepository, ImatgeRepository imatgeRepository, CategoriaRepository categoriaRepository) {
        this.imatgeRepository = imatgeRepository;
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.propietatsRepository = propietatsRepository;
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
    public Set<Direccio> test3(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (session == null) return null;
        return (Set<Direccio>) session.getAttribute("direccions");
    }
}
