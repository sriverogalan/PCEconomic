package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "valors")
public @Data class Valor {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valor")
    private int id;

    @Column(name = "valor")
    private String valor;

    @ManyToMany
    @JoinColumn(name = "id_propietat")
    private Set<Propietat> propietat;

}
