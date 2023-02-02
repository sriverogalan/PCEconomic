package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "persones")
@EqualsAndHashCode(exclude = {"direccions"})
public @Data class Persona {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_persona")
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "cognom1")
    private String surname1;

    @Column(name = "cognom2")
    private String surname2;

    @Column(name = "email", unique = true, nullable = false)
    private String email;

    @Column(name = "contrasenya", unique = true, nullable = false)
    private String password;

    @Column(name = "telefon", unique = true, nullable = false)
    private String telefon;

    @ManyToMany
    @JoinTable(
            name = "persones_direccions",
            joinColumns = @JoinColumn(name = "id_persona"),
            inverseJoinColumns = @JoinColumn(name = "id_direccio")
    )
    private Set<Direccio> direccions;
}

