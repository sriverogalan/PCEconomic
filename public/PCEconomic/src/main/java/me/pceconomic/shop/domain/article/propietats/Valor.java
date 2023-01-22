package me.pceconomic.shop.domain.article.propietats;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "valors")
public @Data class Valor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor")
    private int id;

    @Column(name = "valor")
    private String valor;

    @ManyToOne
    @JoinColumn(name = "id_propietat")
    private Propietat propietat;

}