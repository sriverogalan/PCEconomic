package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.repositories.ArticleRepository;
import me.pceconomic.shop.repositories.CategoriaRepository;
import me.pceconomic.shop.repositories.MarcaRepository;
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
    private final MarcaRepository marcaRepository;

    @Autowired
    public FrontController(CategoriaRepository categoriaRepository, ArticleRepository articleRepository, MarcaRepository marcaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.marcaRepository = marcaRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("articles", articleRepository.findAll());
        return "index";
    }

    @GetMapping("/article/{id}")
    public String article(Model model, @PathVariable int id) {
        model.addAttribute("article", articleRepository.findById(id).get());
        return "article";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        

        Set<Imatge> imatges1 = new HashSet<>();
        Set<Imatge> imatges2 = new HashSet<>();
        Set<Imatge> imatges3 = new HashSet<>();
        imatges1.add(new Imatge("/img/productes/1/1.jpg"));
        imatges2.add(new Imatge("/img/productes/2/2.jpg"));
        imatges3.add(new Imatge("/img/productes/3/3.jpg"));

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
        article.setImatges(imatges1);

        Article article1 = new Article();
        article1.setPes(10);
        article1.setNom("Producte 2");
        article1.setDescripcio("Descripció del producte 2");
        article1.setStockTotal(10);
        article1.setMarca(marca2);
        article1.setImatges(imatges2);

        Article article2 = new Article();
        article2.setPes(10);
        article2.setNom("Producte 3");
        article2.setDescripcio("Descripció del producte 3");
        article2.setStockTotal(10);
        article2.setMarca(marca3);
        article2.setImatges(imatges3);

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
