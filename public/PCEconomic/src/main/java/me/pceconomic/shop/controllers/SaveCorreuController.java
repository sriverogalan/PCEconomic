package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.CorreoNoStock;
import me.pceconomic.shop.repositories.CorreoNoStockRepository;
import me.pceconomic.shop.repositories.PropietatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
public class SaveCorreuController {

    private final CorreoNoStockRepository correoNoStockRepository;
    private final PropietatsRepository propietatsRepository;
    @Autowired
    public SaveCorreuController(CorreoNoStockRepository correoNoStockRepository, PropietatsRepository propietatsRepository) {
        this.correoNoStockRepository = correoNoStockRepository;
        this.propietatsRepository = propietatsRepository;
    }

    @PostMapping("/savecorreu")
    public ResponseEntity<String> saveCorreu(@RequestParam String correu, @RequestParam String id) {
        Propietats propietats = propietatsRepository.findById(Integer.parseInt(id)).orElse(null);

        if (propietats == null) {
            return new ResponseEntity<>("Error al guardar el correo", HttpStatus.BAD_REQUEST);
        }

        if (correoNoStockRepository.existsByEmail(correu)) {
            return new ResponseEntity<>("ALREDY_EXISTS", HttpStatus.BAD_REQUEST);
        }

        CorreoNoStock correoNoStock = new CorreoNoStock();
        correoNoStock.setEmail(correu);
        correoNoStock.setPropietats(propietats);

        correoNoStockRepository.save(correoNoStock);

        return new ResponseEntity<>("Correo guardado correctamente", HttpStatus.OK);
    }
}
