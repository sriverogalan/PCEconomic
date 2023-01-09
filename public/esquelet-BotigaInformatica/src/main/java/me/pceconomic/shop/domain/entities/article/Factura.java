package me.pceconomic.shop.domain.entities.article;

import com.fasterxml.jackson.annotation.JsonEnumDefaultValue;
import jakarta.persistence.*;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

@Entity
public @Data class Factura {

    @Id @GeneratedValue
    @Column(name = "id_factura")
    private int id; 

    @Column(name = "numero_factura")
    private int numeroFactura;

    @Column(name = "preu")
    private double price;

    @Column(name = "quantitat")
    private int quantity;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data")
    private LocalDate data;

    @Column(name = "preu_transport")
    private double preuTransport;

    @Column(name = "estat")
    @Enumerated(EnumType.STRING)
    private EstatFactura estatFactura;

    @OneToMany
    @JoinColumn(name = "id_articles")
    private List<Article> articles;



}
