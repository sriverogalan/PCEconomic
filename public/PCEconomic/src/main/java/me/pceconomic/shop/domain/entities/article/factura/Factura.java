package me.pceconomic.shop.domain.entities.article.factura;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.persona.Administrador;
import me.pceconomic.shop.domain.entities.persona.Client;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public @Data class Factura {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_factura")
    private int id;

    @Column(name = "preu")
    private double preu;

    @Column(name = "quantitat")
    private int quantitat;

    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @Column(name = "metodoPagament")
    private String metodePagament;

    @Column(name = "preuTransport")
    private double preuTransport;

    @Column(name = "estat")
    private String estat;

    @Column(name = "direccio")
    private String direccio;

    @ManyToOne
    private Client client;



}
