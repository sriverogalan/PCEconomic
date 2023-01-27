package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import me.pceconomic.shop.domain.entities.persona.direction.Direccio;

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

    @Column(name = "email")
    private String email;

    @Column(name = "contrasenya")
    private String password;

    @Column(name = "telefon")
    private String telefon;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "id_persona")
    private Set<Direccio> direccions;
}

