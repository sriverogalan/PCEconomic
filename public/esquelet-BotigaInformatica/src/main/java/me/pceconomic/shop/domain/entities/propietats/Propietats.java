package me.pceconomic.shop.domain.entities.propietats;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.Article;

import java.util.List;

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
    @JoinColumn(name = "id_article")
    private Article articles;

    @ManyToOne
    @JoinColumn(name = "id_preu")
    private Preu preus;

    @ManyToOne
    @JoinColumn(name = "id_stock")
    private Stock stock;
}
