package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.categoria.Categoria;
import me.pceconomic.shop.repositories.CategoriaRepository;
import me.pceconomic.shop.repositories.ImatgeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class RestFrontController {
    private final ImatgeRepository imatgeRepository;
    private final CategoriaRepository categoriaRepository;

    @Autowired
    public RestFrontController(ImatgeRepository imatgeRepository, CategoriaRepository categoriaRepository) {
        this.imatgeRepository = imatgeRepository;
        this.categoriaRepository = categoriaRepository;
    }

    @GetMapping("/api/articles")
    public List<Imatge> imatgeRepository() {
        return imatgeRepository.findAll();
    }

    @GetMapping("/api/categories")
    public List<Categoria> categoriaRepository() {
        return categoriaRepository.findAll();
    }
}
