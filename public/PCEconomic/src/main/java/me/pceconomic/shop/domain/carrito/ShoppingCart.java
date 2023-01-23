package me.pceconomic.shop.domain.carrito;

import lombok.Data;

import java.util.List;

public @Data class ShoppingCart {

    private List<Cart> ids;
    private double total;

}