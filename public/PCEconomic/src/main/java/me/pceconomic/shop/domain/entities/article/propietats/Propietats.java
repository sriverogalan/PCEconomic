package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietats")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_valor")
    private Valor valor;

    @OneToMany(mappedBy = "propietats")
    private Set<ArticlePropietat> articlePropietats;

}
