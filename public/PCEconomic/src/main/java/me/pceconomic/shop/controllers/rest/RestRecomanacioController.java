package me.pceconomic.shop.controllers.rest;

import me.pceconomic.shop.domain.entities.article.Visita;
import me.pceconomic.shop.repositories.VisitaRepository;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Set;

@RestController
public class RestRecomanacioController {
    private VisitaRepository visitaRepository;

    public RestRecomanacioController(VisitaRepository visitaRepository) {
        this.visitaRepository = visitaRepository;
    }

    @GetMapping("/api/recomanacio/{id}")
    public List<Visita> recomanacio(@PathVariable int idClient) {
        return visitaRepository.findAllByClientId(idClient);
    }

}
