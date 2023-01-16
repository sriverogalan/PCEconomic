package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

import java.util.Set;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietats")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @OneToMany
    @JoinColumn(name = "id_propietats_valor")
    private Set<PropietatsValor> propietatsValor;

}