package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import me.pceconomic.shop.domain.entities.article.propietats.Propietats;

@Entity(name = "correos_usuarios_no_stock")
public @Data class CorreoNoStock {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_correo")
    private int id;

    @Column(name = "email")
    private String email;

    @OneToOne
    @JoinColumn(name = "id_propietats")
    private Propietats propietats;
}
