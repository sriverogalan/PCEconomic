package me.pceconomic.shop.controllers.rest;

import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.CorreoNoStock;
import me.pceconomic.shop.repositories.CorreoRepository;
import me.pceconomic.shop.repositories.PropietatsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CorreoController {

    private final CorreoRepository correoRepository;
    private final PropietatsRepository propietatsRepository;

    @Autowired
    public CorreoController(CorreoRepository correoRepository, PropietatsRepository propietatsRepository) {
        this.correoRepository = correoRepository;
        this.propietatsRepository = propietatsRepository;
    }

    @PostMapping("/savecorreo/{email}/{idprop}")
    public ResponseEntity<String> saveCorreo(@PathVariable String email, @PathVariable int idprop) {
        Propietats propietats = propietatsRepository.findById(idprop).orElse(null);
        if (propietats == null) return new ResponseEntity<>("No se ha encontrado el producto.", HttpStatus.NOT_FOUND);

        CorreoNoStock correo = new CorreoNoStock();
        correo.setEmail(email);
        correo.setPropietats(propietats);
        correoRepository.save(correo);
        return new ResponseEntity<>("El correo se ha guardado correctamente, se le informará cuando haya stock disponible.", HttpStatus.OK);
    }
}
