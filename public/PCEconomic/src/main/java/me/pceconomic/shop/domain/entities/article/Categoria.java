package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "categories")
@EqualsAndHashCode(exclude = "articles")
public @Data class Categoria {

    @Id
    @GeneratedValue
    @Column(name = "id_categoria")
    private Long id;

    @Column(name = "nom")
    private String name;

    @ManyToOne
    @JoinColumn(name = "categoria_pare")
    private Categoria parent;

    @OneToMany(mappedBy = "parent")
    private Set<Categoria> children;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article articles;
}
