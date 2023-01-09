package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue
    @Column(name = "id_propìetats")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_valor")
    private Valor valor;

    @ManyToOne
    @JoinColumn(name = "id_propietat")
    private Propietat propietat;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article articles;

    @ManyToOne
    @JoinColumn(name = "id_preu")
    private Preu preus;

    @ManyToOne
    @JoinColumn(name = "id_stock")
    private Stock stock;
}
