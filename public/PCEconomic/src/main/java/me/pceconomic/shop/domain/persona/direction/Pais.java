package me.pceconomic.shop.domain.persona.direction;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Set;

@Entity
@Table(name = "paisos")
@EqualsAndHashCode(exclude = {"comunitatsAutonomes"})
public @Data class Pais {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_pais")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_pais")
    private Set<ComunitatAutonoma> comunitatsAutonomes;

    @ManyToOne
    @JoinColumn(name = "id_direccio")
    private Direccio direccio;

}
