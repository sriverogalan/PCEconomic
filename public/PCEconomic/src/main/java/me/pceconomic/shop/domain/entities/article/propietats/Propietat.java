package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;
import lombok.Data;

import java.util.Set;

@Entity
@Table(name = "propietat")
public @Data class Propietat {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_propietat")
    private int id;

    @Column(name = "nom")
    private String nom;

    @OneToMany
    @JoinTable(name = "propietat_valor",
            joinColumns = @JoinColumn(name = "id_propietat"),
            inverseJoinColumns = @JoinColumn(name = "id_valor")
    )
    private Set<Valor> valor;


}
