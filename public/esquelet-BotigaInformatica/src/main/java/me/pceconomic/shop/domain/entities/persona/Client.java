package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.Carrito;

@Entity
@Table(name = "clients")
public @Data class Client {

    @Id @GeneratedValue
    @Column(name = "id_client")
    private int id;

    @Column(name = "dni")
    private String dni;

    @Column(name = "isSuscrit")
    private boolean isSubscribed;

    @Column(name = "isActiu")
    private boolean isActive;

    @OneToOne
    @JoinColumn(name = "id_persona")
    private Persona persona;

}
