package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.*;

@Entity
@Table(name = "preus")
public class Preu {

    @Id @Column(name = "id_preu")
    private int id;

    @Column(name = "preu")
    private double price;

}