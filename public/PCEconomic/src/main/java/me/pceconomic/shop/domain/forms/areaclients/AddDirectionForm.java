package me.pceconomic.shop.domain.forms.areaclients;

import lombok.Data;

public @Data class AddDirectionForm {

    private int id;
    private String nombre;
    private String telefono;
    private String direccion;
    private String calle;
    private String ciudad;
    private String codigoPostal;
    private String pais;
    private String provincia;
    private String principal;

}
