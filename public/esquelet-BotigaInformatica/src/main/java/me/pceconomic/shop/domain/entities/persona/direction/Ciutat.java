package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ciutats")
public @Data class Ciutat {

    @Id @GeneratedValue
    @Column(name = "id_ciutat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToOne
    @JoinColumn(name = "id_codipostal")
    private CodiPostal postalCode;

    @ManyToOne
    @JoinColumn(name = "id_comunitat_autonoma")
    private ComunitatAutonoma comunitatAutonoma;

}
