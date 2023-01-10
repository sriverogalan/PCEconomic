package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"articles"})
public @Data class Carrito {
    @Id
    @GeneratedValue
    @Column(name = "id_carrito")
    private int id;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "preu")
    private double price;

    @OneToMany
    @JoinColumn(name = "id_article")
    private Set<Article> articles;

}
