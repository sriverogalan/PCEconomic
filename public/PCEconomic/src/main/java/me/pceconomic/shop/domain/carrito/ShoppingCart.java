package me.pceconomic.shop.domain.carrito;

import lombok.Data;

import java.util.Set;

public @Data class ShoppingCart {

    private Set<Cart> ids;
    private double total;

}