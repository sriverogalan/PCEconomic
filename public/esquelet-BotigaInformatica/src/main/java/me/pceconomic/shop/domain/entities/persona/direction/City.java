package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "ciutats")
public @Data class City {

    @Id @GeneratedValue
    @Column(name = "id_ciutat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToOne
    @JoinColumn(name = "id_codipostal")
    private PostalCode postalCode;

    @ManyToOne
    @JoinColumn(name = "id_comunitat_autonoma")
    private ComunitatAutonoma comunitatAutonoma;

}
