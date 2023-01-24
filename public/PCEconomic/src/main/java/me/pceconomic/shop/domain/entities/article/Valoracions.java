package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import me.pceconomic.shop.domain.entities.persona.Client;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public class Valoracions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valoracio")
    private int id;

    @ManyToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @Column(name = "valoracio")
    private int valoracio;

    @Column(name = "comentari")
    private String comentari;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_valoracio")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_client")
    private Client client;

}