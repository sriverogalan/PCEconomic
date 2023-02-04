package me.pceconomic.shop.domain.forms.areaclients;

import lombok.Data;

public @Data class AddDirectionForm {

    private String direccion;
    private String calle;
    private String ciudad;
    private String numero;
    private String codigoPostal;
    private String comunidadAutonoma;
    private String pais;
    private String provincia;
    private String principal;

}