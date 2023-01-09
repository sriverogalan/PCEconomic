package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.categories.Categoria;

import java.util.List;

@Entity
@Table(name = "articles")
public @Data class Article {

    @Id @GeneratedValue
    @Column(name = "id_article")
    private int id;

    @Column(name = "nom")
    private String name;

    @Column(name = "descripcio")
    private String description;

    @Column(name = "stockTotal")
    private double totalStock;

    @Column(name = "pes")
    private double weight;

    @OneToMany
    @JoinColumn(name = "id_categoria")
    private List<Categoria> categories;

    @OneToMany
    @JoinColumn(name = "id_oferta")
    private List<Oferta> subcategories;

    @ManyToOne
    @JoinColumn(name = "id_marca")
    private Marca marques;

    @ManyToOne
    @JoinColumn(name = "id_carrito")
    private Carrito carrito;

    @ManyToOne
    @JoinColumn(name = "id_factura")
    private Factura factura;

}