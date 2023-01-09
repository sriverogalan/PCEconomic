package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "valors")
public @Data class Valor {

    @Id @GeneratedValue
    @Column(name = "id_valor")
    private int id;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_tag")
    private Tag tag;

}
