package me.pceconomic.shop.domain.entities.article.propietats;


import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

import java.util.Set;

@Entity
@Embeddable
@Table(name = "article_propietats")
public @Data class ArticlePropietat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_article_propietat")
    private int id;

    @Column(name = "preu")
    private double preu;

    @Column(name = "stock")
    private int stock;

    @Column(name = "id_carrito")
    private int idCarrito;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @OneToMany
    @JoinColumn(name = "id_propietats")
    private Set<Propietat> propietats;
}
