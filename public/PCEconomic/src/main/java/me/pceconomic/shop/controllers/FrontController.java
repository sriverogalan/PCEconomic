package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Categoria;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.*;
import me.pceconomic.shop.services.CreationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.Set;

@Controller
public class FrontController {

    private final CategoriaRepository categoriaRepository;
    private final ArticleRepository articleRepository;
    private final ImatgeRepository imatgeRepository;
    private final PropietatsRepository propietatsRepository;
    private final CreationService creationService;

    @Autowired
    public FrontController(CreationService creationService, PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository ) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.imatgeRepository = imatgeRepository;
        this.propietatsRepository = propietatsRepository;
        this.creationService = creationService;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        model.addAttribute("propietats", propietatsRepository.findAll());
        model.addAttribute("imatges", imatgeRepository.findAll());
        model.addAttribute("categories", categoriaRepository.findAll());
        return "index";
    }

    @GetMapping("/article/{idArticle}/{idPropietat}")
    public String article(Model model, @PathVariable int idArticle, @PathVariable int idPropietat) {
        Article article = articleRepository.findById(idArticle).orElse(null);
        Propietats propietats = propietatsRepository.findById(idPropietat).orElse(null);

        if (article == null || propietats == null) return "error";

        article.getPropietats().forEach(prop -> {
            if (prop.getId() == propietats.getId()) model.addAttribute("propietats", propietats);
        });

        model.addAttribute("categories", categoriaRepository.findAll());
        model.addAttribute("imatges", imatgeRepository.findAll());
        return "article";
    }

    @GetMapping("/categoria/{id}")
    public String getCategories(Model model, @PathVariable int id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);

        Set<Article> articles = articleRepository.findByCategories(categoria);

        Set<Propietats> propietats = new HashSet<>();

        articles.forEach(article -> {
            article.getPropietats().forEach(prop -> {
                propietats.add(prop);
            });
        });

        if (categoria == null || articles == null || propietats == null) return "error";
        model.addAttribute("articles", articles);
        model.addAttribute("propietats", propietats);
        model.addAttribute("imatges", imatgeRepository.findAll());
        model.addAttribute("categories", categoriaRepository.findAll());
        return "index";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        creationService.create();
        return "redirect:/";
    }

}
