package me.pceconomic.shop.domain.other;

import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Imatge;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

import java.util.List;

public @Data class Carrito {

    private int id;
    private int cantidad;
    private List<Propietats> propietatsList;
    private Imatge imatge;
    private int subtotal;
    private double total;

}
