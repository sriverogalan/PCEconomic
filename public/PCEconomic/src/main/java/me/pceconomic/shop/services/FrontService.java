package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.text.NumberFormat;
import java.util.Locale;

@Service
public class FrontService {

    private final CategoriaRepository categoriaRepository;
    private final SubcategoriaRepository subcategoriaRepository;
    private final ArticleRepository articleRepository;
    private final ImatgeRepository imatgeRepository;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public FrontService(SubcategoriaRepository subcategoriaRepository, PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository) {
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
            if (prop.getId() == propietats.getId()) {
                model.addAttribute("propietats", propietats);
                model.addAttribute("preuEuros", formatearComoEuros(prop.getPreu()));
                model.addAttribute("propietatsArticles", propietatsRepository.findAll());
            }
        });

        sendListsToView(model);
    }

    public String formatearComoEuros(double value) {
        NumberFormat formatter = NumberFormat.getCurrencyInstance(new Locale("es", "ES"));
        return formatter.format(value);
    }


    public void getCategoria(Model model, int id) {
        Subcategoria subcategoria = subcategoriaRepository.findById(id).orElse(null);

        if (subcategoria == null) return;

        model.addAttribute("subcategoria", subcategoria);
        sendListsToView(model);
    }

    public void sendListsToView(Model model) {
        model.addAttribute("categories", categoriaRepository.findAll());
        model.addAttribute("subcategories", subcategoriaRepository.findAll());
        model.addAttribute("imatges", imatgeRepository.findAll());
    }

    public void getDireccion(Model model) {
        sendListsToView(model);
    }
}
