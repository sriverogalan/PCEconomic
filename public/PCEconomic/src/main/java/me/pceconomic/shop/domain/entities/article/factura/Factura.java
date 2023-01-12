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
    @GeneratedValue
    @Column(name = "id_factura")
    private int id;

    @Column(name = "numero_factura")
    private int numeroFactura;

    @Column(name = "preu")
    private double price;

    @Column(name = "quantitat")
    private int quantity;


    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @Column(name = "preuTransport")
    private double preuTransport;

    @Column(name = "estat")
    @Enumerated(EnumType.STRING)
    private EstatFactura estatFactura;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Administrador administrador;

}
