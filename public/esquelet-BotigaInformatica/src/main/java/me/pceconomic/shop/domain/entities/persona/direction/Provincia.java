package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Table(name = "provincies")
public @Data class Province {

    @Id @GeneratedValue
    @Column(name = "id_provincia")
    private int id;

    @Column(name = "nom")
    private String name;

    @ManyToOne
    @JoinColumn(name = "id_comunitat_autonoma")
    private ComunitatAutonoma comunitatAutonoma;

}
