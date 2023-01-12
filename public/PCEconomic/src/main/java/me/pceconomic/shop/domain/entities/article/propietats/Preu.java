package me.pceconomic.shop.domain.entities.article.propietats;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "preus")
public class Preu {

    @Id
    @Column(name = "id_preu")
    private int id;

    @Column(name = "preu")
    private double price;

}
