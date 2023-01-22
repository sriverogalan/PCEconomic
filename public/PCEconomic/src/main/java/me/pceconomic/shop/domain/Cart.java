package me.pceconomic.shop.domain;

import lombok.Data;
import me.pceconomic.shop.domain.article.propietats.Propietats;

public @Data class Cart {

    private Propietats propietats;
    private int quantity;
    private double price;

}
