package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.persona.Client;

import java.util.List;

@Entity
public @Data class Carrito {
    @Id
    @GeneratedValue
    @Column(name = "id_carrito")
    private int id;

    @OneToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "preu")
    private double price;

    @OneToMany
    @JoinTable(name = "articles-carrito", joinColumns = @JoinColumn(name = "id_article"), inverseJoinColumns = @JoinColumn(name = "id_carrito") ) // Como hacer mas de un JoinColumn?
    //
    private List<Article> articles;

}
