package me.pceconomic.shop.controllers.rest;

import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
public class RestCarritoController {

    private final CarritoService carritoService;

    @Autowired
    public RestCarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/api/carrito")
    public ShoppingCart getCarrito() {
        System.out.println(Collections.singletonList(carritoService.getCarrito()));
        return carritoService.getCarrito();
    }
}


