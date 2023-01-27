package me.pceconomic.shop.controllers;

import me.pceconomic.shop.domain.carrito.ShoppingCart;
import me.pceconomic.shop.services.CarritoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Objects;

@RestController
public class RestCarritoController {

    private final CarritoService carritoService;

    @Autowired
    public RestCarritoController(CarritoService carritoService) {
        this.carritoService = carritoService;
    }

    @GetMapping("/api/carrito")
    public ShoppingCart getCarrito() {
        return Objects.requireNonNullElseGet((ShoppingCart) carritoService.getSession().getAttribute("carrito"), ShoppingCart::new);
    }
}
