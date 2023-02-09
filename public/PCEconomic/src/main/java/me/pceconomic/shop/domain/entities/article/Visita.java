package me.pceconomic.shop.domain.entities.article;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;
import me.pceconomic.shop.domain.entities.persona.Client;
import java.time.LocalDateTime;

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

    @Column(name = "data_visita")
    private LocalDateTime data;

    @OneToOne
    @JoinColumn(name = "id_article")
    private Article article;

    @OneToOne
    @JoinColumn(name = "id_propietats")
    private Propietats propietats;

}
