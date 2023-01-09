package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "paisos")
public @Data class Pais {

    @Id
    @GeneratedValue
    @Column(name = "id_pais")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_comunitat_autonoma")
    private List<ComunitatAutonoma> comunitatAutonomas;

}