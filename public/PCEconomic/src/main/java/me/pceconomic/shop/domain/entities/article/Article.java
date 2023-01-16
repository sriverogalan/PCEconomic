package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

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

    @Lob // Large Object (BLOB)
    @Column(name = "descripcio", columnDefinition = "LONGTEXT")
    private String descripcio;

    @Column(name = "pes")
    private double pes;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marca;

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "articles_categories",
            joinColumns = @JoinColumn(name = "id_article"),
            inverseJoinColumns = @JoinColumn(name = "id_categoria")
    )
    private Set<Categoria> categories;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "id_article")
    private Set<Propietats> propietats;
}
