package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.HashSet;
import java.util.Set;

@Service
public class FrontService {

    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ArticleRepository articleRepository;
    private final ImatgeRepository imatgeRepository;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public FrontService(SubcategoriaRepository subcategoriaRepository, CreationService creationService, PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.imatgeRepository = imatgeRepository;
        this.propietatsRepository = propietatsRepository;
        this.subcategoriaRepository = subcategoriaRepository;
    }

    public void index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        model.addAttribute("propietats", propietatsRepository.findAll());
        model.addAttribute("imatges", imatgeRepository.findAll());
        model.addAttribute("categories", categoriaRepository.findAll());
        model.addAttribute("subcategories", subcategoriaRepository.findAll());
    }

    public void article(Model model, int idArticle, int idPropietat) {
        Article article = articleRepository.findById(idArticle).orElse(null);
        Propietats propietats = propietatsRepository.findById(idPropietat).orElse(null);

        if (article == null || propietats == null) return;

        article.getPropietats().forEach(prop -> {
            if (prop.getId() == propietats.getId()) model.addAttribute("propietats", propietats);
        });
        sendListsToView(model);
    }

    public void getCategoria(Model model, int id) {
        Subcategoria subcategoria = subcategoriaRepository.findById(id).orElse(null);
        Set<Article> articles = articleRepository.findBySubcategories(subcategoria);
        Set<Propietats> propietats = new HashSet<>();

        if (articles == null) return;

        articles.forEach(article -> {
            propietats.addAll(article.getPropietats());
        });

        if (subcategoria == null) return;

        model.addAttribute("articles", articles);
        model.addAttribute("propietats", propietats);
        sendListsToView(model);
    }

    private void sendListsToView(Model model) {
        model.addAttribute("categories", categoriaRepository.findAll());
        model.addAttribute("subcategories", subcategoriaRepository.findAll());
        model.addAttribute("imatges", imatgeRepository.findAll());
    }

}
