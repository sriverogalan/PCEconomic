package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.propietats.Propietat;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.article.propietats.Valor;
import me.pceconomic.shop.repositories.PropietatRepository;
import me.pceconomic.shop.repositories.PropietatsRepository;
import me.pceconomic.shop.repositories.ValorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    @Autowired
    private PropietatRepository propietatRepository;
    @Autowired
    private PropietatsRepository propietatsRepository;
    @Autowired
    private ValorRepository valorRepository;

    @GetMapping("/test/propietats")
    public List<Propietat> getValors() {
        return propietatRepository.findAll();
    }

    @GetMapping("/test/valors")
    public List<Valor> getProp() {
        return valorRepository.findAll();
    }

    @GetMapping("/test/propietatsssss")
    public List<Propietats> aaaaaa() {
        return propietatsRepository.findAll();
    }

}
