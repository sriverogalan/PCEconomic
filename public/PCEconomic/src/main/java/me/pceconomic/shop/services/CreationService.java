package me.pceconomic.shop.services;

import me.pceconomic.shop.domain.entities.article.Article;
import me.pceconomic.shop.domain.entities.article.Categoria;
import me.pceconomic.shop.domain.entities.article.Marca;
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

    @Autowired
    public CreationService(ArticleRepository articleRepository, MarcaRepository marcaRepository, CategoriaRepository categoriaRepository, PropietatsRepository propietatsRepository, ValorRepository valorRepository, PropietatRepository propietatRepository) {
        this.articleRepository = articleRepository;
        this.marcaRepository = marcaRepository;
        this.categoriaRepository = categoriaRepository;
        this.propietatsRepository = propietatsRepository;
        this.valorRepository = valorRepository;
        this.propietatRepository = propietatRepository;
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


        Categoria ram = new Categoria();
        ram.setName("RAM");
        Categoria iphone13 = new Categoria();
        iphone13.setName("Iphone 13");

        Categoria informatica = new Categoria();
        informatica.setName("Informatica");
        informatica.setChildren(Set.of(ram));
        informatica.setChildren(Set.of(iphone13));

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

        Valor valor = new Valor();
        valor.setValor("S");

        Valor valor2 = new Valor();
        valor.setValor("M");

        Valor valor3 = new Valor();
        valor.setValor("Blau");

        Valor valor4 = new Valor();
        valor.setValor("Negre");

        valorRepository.save(valor);
        valorRepository.save(valor2);
        valorRepository.save(valor3);
        valorRepository.save(valor4);

        Propietat propietat = new Propietat();
        propietat.setNom("Talla");
        propietat.setValor(Set.of(valor, valor2));

        Propietat propietat2 = new Propietat();
        propietat2.setNom("Color");
        propietat2.setValor(Set.of(valor3, valor4));

        propietatRepository.save(propietat);
        propietatRepository.save(propietat2);

        Propietats propietats = new Propietats();
        propietats.setPropietat(Set.of(propietat, propietat2));
        propietats.setArticle(article);
        propietats.setPreu(100);
        propietats.setStock(10);

        Propietats propietats1 = new Propietats();
        propietats1.setPropietat(Set.of(propietat, propietat2));
        propietats1.setArticle(article1);
        propietats1.setPreu(1000);
        propietats1.setStock(10);

        propietatsRepository.save(propietats);
        propietatsRepository.save(propietats1);
    }

}
