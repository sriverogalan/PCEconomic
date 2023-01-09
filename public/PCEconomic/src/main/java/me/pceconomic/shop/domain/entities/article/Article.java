package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "articles")
public @Data class Article {
    @Id
    @GeneratedValue
    @Column(name = "id_article")
    private int id;
    @Column(name = "nom")
    private String nom;

    @Column(name = "descripcio")
    private String descripcio;

    @Column(name = "stockTotal")
    private double stockTotal;

    @Column(name = "pes")
    private double pes;
}
