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

    @Column(name = "numero_factura")
    private int numeroFactura;

    @Column(name = "preu")
    private double price;

    @Column(name = "quantitat")
    private int quantity;

    @Column(name = "data")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private LocalDate data;

    @Column(name = "paymentMethod")
    private String paymentMethod;

    @Column(name = "paymentStatus")
    private String paymentStatus;

    @Column(name = "preuTransport")
    private double preuTransport;

    @Column(name = "estat")
    private String estat;

    @ManyToOne
    private Client client;

    @ManyToOne
    private Administrador administrador;

}
