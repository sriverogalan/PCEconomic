package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.List;
import java.util.Set;

@Entity
@Table(name = "comunitats_autonomes")
@EqualsAndHashCode(exclude = {"provincies"})
public @Data class ComunitatAutonoma {

    @Id @GeneratedValue
    @Column(name = "id_comunitat_autonoma")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_provincia")
    private Set<Provincia> provincies;

}
