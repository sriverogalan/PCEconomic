package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Categoria;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.HashSet;
import java.util.List;
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

    @GetMapping("/article/{id}/{propietat}")
    public String article(Model model, @PathVariable int id, @PathVariable int propietat) {
        Propietats propietats = propietatsRepository.findById(propietat).orElse(null);

        if (propietats == null) return "error";

        Article article = propietats.getArticle();

        if (article == null) return "error";

        model.addAttribute("article", article);
        model.addAttribute("propietats", propietats);
        model.addAttribute("categories", categoriaRepository.findAll());
        return "article";
    }

    @GetMapping("/article/{id}")
    public String article(Model model, @PathVariable int id) {
        List<Propietats> propietats = propietatsRepository.findAll();

        propietats.forEach(prop -> {
            if (prop.getArticle().getId() == id) {
                model.addAttribute("article", prop.getArticle());
            }
        });

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
        Marca marca1 = new Marca("12345678A", "Corsair", null);
        Marca marca2 = new Marca("87654321B", "Iphone", null);
        Marca marca3 = new Marca("12345678C", "Marca 3", null);
        Marca marca4 = new Marca("87654321D", "Marca 4", null);

        marcaRepository.save(marca1);
        marcaRepository.save(marca2);
        marcaRepository.save(marca3);
        marcaRepository.save(marca4);


        Categoria ram = new Categoria();
        ram.setName("RAM");
        Categoria iphone13 = new Categoria();
        iphone13.setName("Iphone 13");

        Categoria informatica = new Categoria();
        informatica.setName("Informatica");
        informatica.setChildren((Set<Categoria>) ram);
        informatica.setChildren((Set<Categoria>) iphone13);

        categoriaRepository.save(informatica);
        categoriaRepository.save(ram);
        categoriaRepository.save(iphone13);

        Article article = new Article();
        article.setPes(10);
        article.setNom("Corsair CMK16GX4M2B3200C16 Vengeance LPX 16 GB (2 x 8 GB) DDR4 3200 MHz C16 XMP 2.0 Módulo de Memoria de Alto Rendimiento");
        article.setDescripcio("Descripció del producte 1");
        article.setMarca(marca1);

        Article article1 = new Article();
        article1.setPes(10);
        article1.setNom("Iphone 13");
        article1.setDescripcio("Pantalla Super Retina XDR de 6,1 pulgadas\n" +
                "El modo Cine añade poca profundidad de campo y cambia el enfoque automáticamente en los vídeos\n" +
                "Sistema avanzado de cámara dual de 12 Mpx con gran angular y ultra gran angular, Estilos Fotográficos, HDR Inteligente 4, modo Noche y grabación de vídeo en 4K HDR con Dolby Vision\n" +
                "Cámara delantera TrueDepth de 12 Mpx con modo Noche y grabación de vídeo en 4K HDR con Dolby Vision\n" +
                "Chip A15 Bionic para un rendimiento ultrarrápido\n" +
                "Hasta 19 horas de reproducción de vídeo\n" +
                "Diseño robusto con Ceramic Shield");
        article1.setMarca(marca2);

        articleRepository.save(article);
        articleRepository.save(article1);

        /*imatge.setPath("/img/productes/1/1/0.jpg");
        imatge1.setPath("/img/productes/1/1/1.jpg");

        imatge2.setPath("/img/productes/1/2/0.jpg");

        imatgeRepository.save(imatge);
        imatgeRepository.save(imatge1);
        imatgeRepository.save(imatge2);
        imatgeRepository.save(imatge3);
        imatgeRepository.save(imatge4);

        article.getImatges().add(imatge);
        article.getImatges().add(imatge1);
        article1.getImatges().add(imatge2);
        article2.getImatges().add(imatge3);
        article3.getImatges().add(imatge4);*/

        articleRepository.save(article);
        articleRepository.save(article1);
        return "redirect:/";
    }

}
