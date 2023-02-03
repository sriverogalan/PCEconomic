package me.pceconomic.shop.controllers.rest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

@RestController
public class RestTransportController {

    @Value("${transporte.precioBase}")
    private double precioBase;

    @Value("${transporte.precioKm}")
    private double precioKm;

    @Value("${transporte.precioKg}")
    private double precioKg;

    @Value("${transporte.precioPaquete}")
    private double precioPaquete;


    @GetMapping("/api/transport")
    public String transport(@RequestParam double pesTotal, @RequestParam double distanciaTotal, @RequestParam int numPaquets) {
        if (pesTotal < 0 || distanciaTotal < 0 || numPaquets < 0) {
            return "Error: No es pot calcular el transport";
        }
        double preuTransport = precioBase + distanciaTotal * precioKm + pesTotal * precioKg + numPaquets * precioPaquete;

        return String.valueOf(preuTransport);
    }

}
