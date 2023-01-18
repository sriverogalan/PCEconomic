package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.Marca;
import me.pceconomic.shop.domain.entities.article.categoria.Categoria;
import me.pceconomic.shop.domain.entities.article.categoria.Subcategoria;
import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.article.propietats.Valor;
import me.pceconomic.shop.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class CreationService {

    private final ArticleRepository articleRepository;
    private final MarcaRepository marcaRepository;
    private final CategoriaRepository categoriaRepository;
    private final PropietatsRepository propietatsRepository;
    private final ValorRepository valorRepository;
    private final PropietatRepository propietatRepository;
    private final ImatgeRepository imatgeRepository;

    @Autowired
    public CreationService(ImatgeRepository imatgeRepository, ArticleRepository articleRepository, MarcaRepository marcaRepository, CategoriaRepository categoriaRepository, PropietatsRepository propietatsRepository, ValorRepository valorRepository, PropietatRepository propietatRepository) {
        this.articleRepository = articleRepository;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.propietatsRepository = propietatsRepository;
        this.valorRepository = valorRepository;
        this.propietatRepository = propietatRepository;
        this.imatgeRepository = imatgeRepository;
    }


    public void create() {
        Marca marca1 = new Marca("12345678A", "Corsair", null);
        Marca marca2 = new Marca("87654321B", "Iphone", null);
        Marca marca3 = new Marca("12345678C", "Marca 3", null);
        Marca marca4 = new Marca("87654321D", "Marca 4", null);

        marcaRepository.save(marca1);
        marcaRepository.save(marca2);
        marcaRepository.save(marca3);
        marcaRepository.save(marca4);

        Subcategoria ram = new Subcategoria();
        ram.setName("RAM");
        Subcategoria iphone13 = new Subcategoria();
        iphone13.setName("Iphone 13");

        Categoria informatica = new Categoria();
        informatica.setName("Informatica");
        informatica.setSubcategorias(Set.of(ram, iphone13));

        categoriaRepository.save(informatica);

        Article article = new Article();
        article.setPes(10);
        article.setNom("Corsair CMK16GX4M2B3200C16 Vengeance LPX 16 GB (2 x 8 GB) DDR4 3200 MHz C16 XMP 2.0 Módulo de Memoria de Alto Rendimiento");
        article.setDescripcio("Corsair CMK16GX4M2B3200C16 Vengeance LPX 16 GB (2 x 8 GB) DDR4 3200 MHz C16 XMP 2.0 Módulo de Memoria de Alto Rendimiento");
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

        Propietat propietat = new Propietat();
        propietat.setNom("Talla");

        Propietat propietat2 = new Propietat();
        propietat2.setNom("Color");

        propietatRepository.save(propietat);
        propietatRepository.save(propietat2);

        Valor S = new Valor();
        S.setValor("S");
        S.setPropietat(propietat);

        Valor M = new Valor();
        M.setValor("M");
        M.setPropietat(propietat);

        Valor blau = new Valor();
        blau.setValor("Blau");
        blau.setPropietat(propietat2);

        Valor negre = new Valor();
        negre.setValor("Negre");
        negre.setPropietat(propietat2);

        valorRepository.save(S);
        valorRepository.save(M);
        valorRepository.save(blau);
        valorRepository.save(negre);

        Propietats corsair = new Propietats();
        corsair.setArticle(article);
        corsair.setPreu(100);
        corsair.setStock(10);
        corsair.setValor(Set.of(M, negre));

        Propietats propietats1 = new Propietats();
        propietats1.setArticle(article1);
        propietats1.setPreu(1000);
        propietats1.setStock(10);
        propietats1.setValor(Set.of(S, blau));

        propietatsRepository.save(corsair);
        propietatsRepository.save(propietats1);

        Imatge corsairNegre = new Imatge();
        corsairNegre.setPath("0.jpg");
        corsairNegre.setPropietats(corsair);
        corsairNegre.setPrincipal(true);

        Imatge corsairNegre2 = new Imatge();
        corsairNegre2.setPath("1.jpg");
        corsairNegre2.setPropietats(corsair);

        Imatge corsairNegre3 = new Imatge();
        corsairNegre3.setPath("2.jpg");
        corsairNegre3.setPropietats(corsair);

        Imatge iphone13blau = new Imatge();
        iphone13blau.setPath("0.jpg");
        iphone13blau.setPropietats(propietats1);
        iphone13blau.setPrincipal(true);

        Imatge iphone13blau2 = new Imatge();
        iphone13blau2.setPath("1.jpg");
        iphone13blau2.setPropietats(propietats1);

        imatgeRepository.save(corsairNegre);
        imatgeRepository.save(corsairNegre2);
        imatgeRepository.save(corsairNegre3);

        imatgeRepository.save(iphone13blau);
        imatgeRepository.save(iphone13blau2);
    }
}
