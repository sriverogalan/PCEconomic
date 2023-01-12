package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Article;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue
    @Column(name = "id_propietats")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_valor")
    private Valor valor;

    @Column(name = "preu")
    private int preu;

    @ManyToOne
    @JoinColumn(name = "id_stock")
    private Stock stock;

}
