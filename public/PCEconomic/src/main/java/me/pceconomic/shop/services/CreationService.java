package me.pceconomic.shop.services;

import jakarta.transaction.Transactional;
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

    @Transactional
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
        article1.setDescripcio("Pantalla Super Retina XDR de 6,1 pulgadas" +
                "El modo Cine añade poca profundidad de campo y cambia el enfoque automáticamente en los vídeos" +
                "Sistema avanzado de cámara dual de 12 Mpx con gran angular y ultra gran angular, Estilos Fotográficos, HDR Inteligente 4, modo Noche y grabación de vídeo en 4K HDR con Dolby Vision" +
                "Cámara delantera TrueDepth de 12 Mpx con modo Noche y grabación de vídeo en 4K HDR con Dolby Vision" +
                "Chip A15 Bionic para un rendimiento ultrarrápido" +
                "Hasta 19 horas de reproducción de vídeo" +
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

    }

    public void crear1000() {
        for (int i = 0; i < 1000; i++) {
            this.create();
        }
    }
}
