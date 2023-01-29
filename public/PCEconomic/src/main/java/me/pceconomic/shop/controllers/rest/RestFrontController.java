package me.pceconomic.shop.controllers.rest;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.categoria.Categoria;
import me.pceconomic.shop.repositories.ArticleRepository;
import me.pceconomic.shop.repositories.CategoriaRepository;
import me.pceconomic.shop.repositories.ImatgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestFrontController {
    private final ImatgeRepository imatgeRepository;
    private final CategoriaRepository categoriaRepository;
    private final ArticleRepository articleRepository;

    @Autowired
    public RestFrontController(ArticleRepository articleRepository, ImatgeRepository imatgeRepository, CategoriaRepository categoriaRepository) {
        this.imatgeRepository = imatgeRepository;
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
    }

    @GetMapping("/api/articles")
    public List<Article> imatgeRepository() {
        return articleRepository.findAll();
    }

    @GetMapping("/api/categories")
    public List<Categoria> categoriaRepository() {
        return categoriaRepository.findAll();
    }
}
