package me.pceconomic.shop.domain.carrito;

import lombok.Data;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

public @Data class Cart {

    private Propietats propietats;
    private int quantity;
    private double price;

}
