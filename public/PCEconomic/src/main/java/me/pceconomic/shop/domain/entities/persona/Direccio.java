package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.pceconomic.shop.domain.forms.AddDirectionForm;

@Entity
@Table(name = "direccions")
@NoArgsConstructor
public @Data class Direccio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccio")
    private int id;

    @Column(name = "carrer")
    private String street;

    @Column(name = "numero")
    private String number;

    @Column(name = "estaActiva")
    private boolean isActive;

    @Column(name = "ciutat")
    private String city;

    @Column(name = "provincia")
    private String province;

    @Column(name = "codi_postal")
    private String postalCode;

    @Column(name = "pais")
    private String country;

    @Column(name = "comunitat_autonoma")
    private String autonomousCommunity;

    @Column(name = "notes")
    private String notes;

    public Direccio(AddDirectionForm directionForm) {
        this.street = directionForm.getCalle();
        this.number = directionForm.getNumero();
        this.city = directionForm.getCiudad();
        this.province = directionForm.getProvincia();
        this.postalCode = directionForm.getCodigoPostal();
        this.country = directionForm.getPais();
        this.autonomousCommunity = directionForm.getComunidadAutonoma();
        this.isActive = directionForm.isPrincipal();
    }
}
