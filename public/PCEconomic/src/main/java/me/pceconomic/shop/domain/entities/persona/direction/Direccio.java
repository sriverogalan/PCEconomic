package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.persona.Persona;

import java.util.Set;

@Entity
@Table(name = "direccions")
@EqualsAndHashCode(exclude = "countries")
public @Data class Direccio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccio")
    private int id;

    @Column(name = "carrer")
    private String street;

    @Column(name = "numero")
    private int number;

    @Column(name = "estaActiva")
    private boolean isActive;

    @OneToMany
    @JoinColumn(name = "id_direccio")
    private Set<Pais> countries;

    @ManyToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;
}
