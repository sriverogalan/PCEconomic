package me.pceconomic.shop.domain.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "comunitats_autonomes")
@EqualsAndHashCode(exclude = {"provincies"})
public @Data class ComunitatAutonoma {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comunitat_autonoma")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_comunitat_autonoma")
    private Set<Provincia> provincies;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais pais;

}