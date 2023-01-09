package me.pceconomic.shop.domain.entities.persona.direction;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Table(name = "comunitats_autonomes")
public @Data class ComunitatAutonoma {

    @Id @GeneratedValue
    @Column(name = "id_comunitat_autonoma")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_ciutat")
    private List<Ciutat> cities;

    @ManyToOne
    @JoinColumn(name = "id_pais")
    private Pais country;

    @OneToMany
    @JoinColumn(name = "id_provincia")
    private List<Provincia> provinces;

}
