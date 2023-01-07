package me.pceconomic.shop.domain.entities.propietats;

import jakarta.persistence.*;

@Entity
@Table(name = "preus")
public class Preu {

    @Id
    private int id;

    @Column(name = "preu")
    private double price;

    @ManyToOne
    @JoinColumn(name = "id_propietat")
    private Propietats propietats;

}
