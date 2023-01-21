package me.pceconomic.shop.domain.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "provincies")
@EqualsAndHashCode(exclude = {"ciutats"})
public @Data class Provincia {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_provincia")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_provincia")
    private Set<Ciutat> ciutats;

    @ManyToOne
    @JoinColumn(name = "id_comunitat_autonoma")
    private ComunitatAutonoma comunitatAutonoma;

}
