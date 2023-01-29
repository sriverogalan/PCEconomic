package me.pceconomic.shop.controllers.rest;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class RestTransportController {

    @GetMapping ("/api/transport/{pesTotal}/{distanciaTotal}/{numPaquets}")
    // peso kg  distancia km  numero de paquetes
    public String transport(@PathVariable double pesTotal, @PathVariable double distanciaTotal, @PathVariable int numPaquets) {
        if (pesTotal < 0 || distanciaTotal < 0 || numPaquets < 0) {
            return "Error: No es pot calcular el transport amb valors negatius";
        }
        // poner num en properties
        double preuBase = 2;
        double distanciaCada50km = distanciaTotal / 50;
        double pesCada5kg = pesTotal / 5;
        double preuTransport = preuBase + distanciaCada50km + pesCada5kg; // 2€ fixos + 0.02€/km + 0.2€/kg

        return String.valueOf(preuTransport);
    }

}
