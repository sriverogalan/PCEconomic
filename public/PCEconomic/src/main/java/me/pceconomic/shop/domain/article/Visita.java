package me.pceconomic.shop.domain.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.persona.Client;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public @Data class Visita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_visita")
    private int id;

    @Column(name = "numVisites")
    private int numVisites;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_visita")
    private LocalDate data;

    @OneToOne
    @JoinColumn(name = "id_article")
    private Article article;
}