package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.repositories.ArticleRepository;
import me.pceconomic.shop.repositories.CategoriaRepository;
import me.pceconomic.shop.repositories.ImatgeRepository;
import me.pceconomic.shop.repositories.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;

@Controller
public class FrontController {

    private final CategoriaRepository categoriaRepository;
    private final ArticleRepository articleRepository;
    private final MarcaRepository marcaRepository;
    private final ImatgeRepository imatgeRepository;

    @Autowired
    public FrontController(CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository, MarcaRepository marcaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.marcaRepository = marcaRepository;
        this.imatgeRepository = imatgeRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @GetMapping("/article/{id}")
    public String article(Model model, @PathVariable int id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) return "404";

        model.addAttribute("article", article);
        return "article";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        Marca marca1 = new Marca("12345678A", "Marca 1", null);
        Marca marca2 = new Marca("87654321B", "Marca 2", null);
        Marca marca3 = new Marca("12345678C", "Marca 3", null);

        marcaRepository.save(marca1);
        marcaRepository.save(marca2);
        marcaRepository.save(marca3);

        Article article = new Article();
        article.setPes(10);
        article.setNom("Producte 1");
        article.setDescripcio("Descripció del producte 1");
        article.setStockTotal(10);
        article.setMarca(marca1);
        article.setImatges(new HashSet<>());

        Article article1 = new Article();
        article1.setPes(10);
        article1.setNom("Producte 2");
        article1.setDescripcio("Descripció del producte 2");
        article1.setStockTotal(10);
        article1.setMarca(marca2);
        article1.setImatges(new HashSet<>());

        Article article2 = new Article();
        article2.setPes(10);
        article2.setNom("Producte 3");
        article2.setDescripcio("Descripció del producte 3");
        article2.setStockTotal(10);
        article2.setMarca(marca3);
        article2.setImatges(new HashSet<>());

        articleRepository.save(article);
        articleRepository.save(article1);
        articleRepository.save(article2);

        Imatge imatge = new Imatge();
        imatge.setIdArticle(article.getId());
        imatge.setPath("/img/productes/1/1.jpg");

        Imatge imatge2 = new Imatge();
        imatge2.setIdArticle(article.getId());
        imatge2.setPath("/img/productes/2/1.jpg");

        Imatge imatge3 = new Imatge();
        imatge3.setIdArticle(article.getId());
        imatge3.setPath("/img/productes/3/1.jpg");

        imatgeRepository.save(imatge);
        imatgeRepository.save(imatge2);
        imatgeRepository.save(imatge3);

        article.getImatges().add(imatge);
        article1.getImatges().add(imatge2);
        article2.getImatges().add(imatge3);

        articleRepository.save(article);
        articleRepository.save(article1);
        articleRepository.save(article2);
        return "redirect:/";
    }

    @GetMapping("/register")
    public String register() {
        return "register";
    }

}
