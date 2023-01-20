package me.pceconomic.shop.domain.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;

import java.util.Set;

@Entity
@Table(name = "marques")
@EqualsAndHashCode(exclude = "articles")
@NoArgsConstructor
public @Data class Marca {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_marca")
    private int id;

    @Column(name = "cif")
    private String cif;

    @Column(name = "nom")
    private String name;

    @Column(name = "web")
    private String web;

    @OneToMany(mappedBy = "marca")
    @Fetch(FetchMode.JOIN)
    private Set<Article> articles;

    public Marca(String cif, String name, Set<Article> articles) {
        this.cif = cif;
        this.name = name;
        this.articles = articles;
    }


}