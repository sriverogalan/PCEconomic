package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "marques")
@EqualsAndHashCode(exclude = "articles")
public @Data class Marca {

    @Id
    @GeneratedValue
    @Column(name = "id_marca")
    private int id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "nom")
    private String name;

    @OneToMany(mappedBy = "marca")
    private Set<Article> articles;

}
