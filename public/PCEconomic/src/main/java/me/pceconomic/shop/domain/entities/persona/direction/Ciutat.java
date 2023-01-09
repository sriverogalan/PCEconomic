package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "ciutats")
public @Data class Ciutat {

    @Id @GeneratedValue
    @Column(name = "id_ciutat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_codipostal")
    private List<CodiPostal> codiPostal;

}
