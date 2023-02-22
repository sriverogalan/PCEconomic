package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Persona;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@Entity
public @Data class Valoracions {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_valoracio")
    private int id;

    @Column(name = "valoracio")
    private double valoracio;

    @Column(name = "comentari")
    private String comentari;

    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @Column(name = "data_valoracio")
    private LocalDate data;

    @ManyToOne
    @JoinColumn(name = "id_propietat")
    private Propietats propietats;

    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinColumn(name = "id_persona")
    private Persona persona;

}
