package com.CRUDProductes.models;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = {"tipus", "proveidor", "detallProducte"})
@AllArgsConstructor
@NoArgsConstructor
public @Data class Producte {
    @Id
    @GeneratedValue
    private Long id;
    @Column(name = "nom_producte")
    private String nom;
    @Column(name = "descripcio")
    private String descripcio;
    @Column(name = "preu")
    private double preu;
    @ManyToOne
    private Proveidor proveidor;
    @ManyToMany
    private Set<Tipus> tipus;
    @OneToOne
    private DetallProducte detallProducte;
}