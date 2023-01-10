package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

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

    @ManyToOne
    @JoinColumn(name = "id_propietats")
    private Propietats propietats;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;
}
