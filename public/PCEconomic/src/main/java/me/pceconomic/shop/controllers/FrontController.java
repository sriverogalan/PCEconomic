package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Categoria;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.repositories.*;
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
    private final ImatgeRepository imatgeRepository;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public FrontController(PropietatsRepository propietatsRepository, CategoriaRepository categoriaRepository, ImatgeRepository imatgeRepository, ArticleRepository articleRepository, MarcaRepository marcaRepository) {
        this.categoriaRepository = categoriaRepository;
        this.articleRepository = articleRepository;
        this.marcaRepository = marcaRepository;
        this.imatgeRepository = imatgeRepository;
        this.propietatsRepository = propietatsRepository;
    }

    @GetMapping("/")
    public String index(Model model) {
        model.addAttribute("categories", categoriaRepository.findAll());
        model.addAttribute("articles", propietatsRepository.findAll());
        return "index";
    }

    @GetMapping("/article/{id}")
    public String article(Model model, @PathVariable int id) {
        Article article = articleRepository.findById(id).orElse(null);
        if (article == null) return "error";
        model.addAttribute("article", article);
        model.addAttribute("categories", categoriaRepository.findAll());
        return "article";
    }

    @GetMapping("/categoria/{id}")
    public String getCategories(Model model, @PathVariable int id) {
        Categoria categoria = categoriaRepository.findById(id).orElse(null);
        Set<Article> articles = articleRepository.findByCategories(categoria);
        if (categoria == null || articles == null) return "error";
        model.addAttribute("articles", articles);
        model.addAttribute("categories", categoriaRepository.findAll());
        return "categories";
    }

    @GetMapping("/crearproducte")
    public String createProducts() {
        Marca marca1 = new Marca("12345678A", "Marca 1", null);
        Marca marca2 = new Marca("87654321B", "Marca 2", null);
        Marca marca3 = new Marca("12345678C", "Marca 3", null);
        Marca marca4 = new Marca("87654321D", "Marca 4", null);

        marcaRepository.save(marca1);
        marcaRepository.save(marca2);
        marcaRepository.save(marca3);
        marcaRepository.save(marca4);

        Categoria categoria1 = new Categoria();
        categoria1.setName("Categoria 1");
        categoria1.setChildren(null);

        Categoria categoria2 = new Categoria();
        categoria2.setName("Categoria 2");
        categoria2.setChildren(null);

        Categoria categoria3 = new Categoria();
        categoria3.setName("Categoria 3");
        categoria3.setChildren(null);

        Categoria categoria4 = new Categoria();
        categoria4.setName("Categoria 4");
        categoria4.setChildren(null);

        Set<Categoria> categories1 = new HashSet<>();
        categories1.add(categoria1);
        Set<Categoria> categories2 = new HashSet<>();
        categories2.add(categoria2);
        Set<Categoria> categories3 = new HashSet<>();
        categories3.add(categoria3);
        Set<Categoria> categories4 = new HashSet<>();
        categories4.add(categoria4);

        categoriaRepository.save(categoria1);
        categoriaRepository.save(categoria2);
        categoriaRepository.save(categoria3);
        categoriaRepository.save(categoria4);

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

        Article article3 = new Article();
        article3.setPes(10);
        article3.setNom("Producte 4");
        article3.setDescripcio("Descripció del producte 4");
        article3.setStockTotal(40);
        article3.setMarca(marca4);
        article3.setImatges(new HashSet<>());

        articleRepository.save(article);
        articleRepository.save(article1);
        articleRepository.save(article2);

        Imatge imatge = new Imatge();
        imatge.setIdArticle(article.getId());
        imatge.setPath("/img/productes/1/0.jpg");

        Imatge imatge1 = new Imatge();
        imatge1.setIdArticle(article1.getId());
        imatge1.setPath("/img/productes/1/1.jpg");

        Imatge imatge2 = new Imatge();
        imatge2.setIdArticle(article.getId());
        imatge2.setPath("/img/productes/2/0.jpg");

        Imatge imatge3 = new Imatge();
        imatge3.setIdArticle(article.getId());
        imatge3.setPath("/img/productes/3/0.jpg");

        Imatge imatge4 = new Imatge();
        imatge4.setIdArticle(article.getId());
        imatge4.setPath("/img/productes/4/0.jpg");

        imatgeRepository.save(imatge);
        imatgeRepository.save(imatge1);
        imatgeRepository.save(imatge2);
        imatgeRepository.save(imatge3);
        imatgeRepository.save(imatge4);

        article.getImatges().add(imatge);
        article.getImatges().add(imatge1);
        article1.getImatges().add(imatge2);
        article2.getImatges().add(imatge3);
        article3.getImatges().add(imatge4);

        article.setCategories(categories1);
        article1.setCategories(categories1);
        article1.setCategories(categories2);
        article2.setCategories(categories3);
        article3.setCategories(categories4);

        articleRepository.save(article);
        articleRepository.save(article1);
        articleRepository.save(article2);
        articleRepository.save(article3);
        return "redirect:/";
    }

}
