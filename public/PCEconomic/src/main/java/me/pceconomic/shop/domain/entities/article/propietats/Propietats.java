package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Preu;

@Entity
@Table(name = "propietats")
public @Data class Propietats {
    @Id
    @GeneratedValue
    @Column(name = "id_propietat")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "id_valor")
    private Valor valor;

    @ManyToOne
    @JoinColumn(name = "id_preu")
    private Preu preus;

    @ManyToOne
    @JoinColumn(name = "id_stock")
    private Stock stock;

}
