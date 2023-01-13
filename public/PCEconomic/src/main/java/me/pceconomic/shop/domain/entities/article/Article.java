package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.propietats.ArticlePropietat;

import java.util.Set;

@Entity
@Table(name = "articles")
@EqualsAndHashCode(exclude = {"propietats"})
public @Data class Article {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
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
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_article")
    private Set<Imatge> imatges;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_categoria")
    private Set<Categoria> categories;

    @OneToMany(mappedBy = "article", cascade = CascadeType.ALL)
    private Set<ArticlePropietat> propietats;
}
