package me.pceconomic.shop.domain.entities.propietats;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "propietat")
public class Propietat {

    @Id @GeneratedValue
    @Column(name = "id_propietat")
    private int id;

    @Column(name = "nom")
    private String name;

    @OneToMany
    @JoinColumn(name = "id_valor")
    private List<Valor> valor;

    @OneToMany
    @JoinColumn(name = "id_propietats")
    private List<Propietats> propietats;

}
