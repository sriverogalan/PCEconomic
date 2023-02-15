package me.pceconomic.shop.domain.entities.article.factura;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

@Entity
@EqualsAndHashCode(exclude = "lineasFacturas")
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
    @JsonIgnore
    private Client client;

    @OneToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private Set<LineasFactura> lineasFacturas = new HashSet<>();

    public void addLineasFactura(LineasFactura lineasFactura) {
        lineasFactura.setFactura(this);
        this.lineasFacturas.add(lineasFactura);
    }

}
