package me.pceconomic.shop.domain;

import lombok.Data;

import java.util.List;

public @Data class ShoppingCart {

    private List<Cart> ids;

}
