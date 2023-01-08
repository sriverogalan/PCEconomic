package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "persones")
@Getter @Setter
public class Persona {
    @Id @GeneratedValue
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

    @OneToMany
    @JoinColumn(name = "id_direccio")
    private List<Direccio> direccions;


}

