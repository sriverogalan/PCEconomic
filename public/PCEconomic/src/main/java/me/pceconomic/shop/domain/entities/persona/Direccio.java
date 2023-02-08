package me.pceconomic.shop.domain.entities.persona;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import me.pceconomic.shop.domain.forms.areaclients.AddDirectionForm;

@Entity
@Table(name = "direccions")
@NoArgsConstructor
public @Data class Direccio {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_direccio")
    private int id;

    @Column(name = "nom_complet")
    private String fullName;

    @Column(name = "telefon")
    private String phone;

    @Column(name = "carrer")
    private String streetandnumber;

    @Column(name = "estaActiva")
    private boolean isPrincipal;

    @Column(name = "ciutat")
    private String city;

    @Column(name = "provincia")
    private String province;

    @Column(name = "codi_postal")
    private String postalCode;

    @Column(name = "pais")
    private String country;

    @Column(name = "notes")
    private String notes;

    @Column(name = "direccio")
    private String address;

    public Direccio(AddDirectionForm directionForm) {
        this.fullName = directionForm.getNombre();
        this.phone = directionForm.getTelefono();
        this.streetandnumber = directionForm.getCalle();
        this.city = directionForm.getCiudad();
        this.province = directionForm.getProvincia();
        this.postalCode = directionForm.getCodigoPostal();
        this.country = directionForm.getPais();
        this.isPrincipal = Boolean.parseBoolean(directionForm.getPrincipal());
        this.address = directionForm.getDireccion();
    }
}
