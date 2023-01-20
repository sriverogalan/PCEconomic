package me.pceconomic.shop.domain.article;

import jakarta.persistence.*;
import lombok.Data;

@Entity
public @Data class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private int id;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "preu")
    private double price;

}
