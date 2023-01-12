package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "ciutats")
@EqualsAndHashCode(exclude = "codiPostal")
public @Data class Ciutat {

    @Id
    @GeneratedValue
    @Column(name = "id_ciutat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_codipostal")
    private Set<CodiPostal> codiPostal;

}
