package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.propietats.ArticlePropietat;

import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"articles"})
public @Data class Carrito {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrito")
    private int id;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "preu")
    private double price;

    @OneToMany
    @JoinColumn(name = "id_article_propietat")
    private Set<ArticlePropietat> articlePropietats;

}
