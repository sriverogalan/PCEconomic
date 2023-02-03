package me.pceconomic.shop.domain.forms;

import lombok.Getter;

@Getter
public class AddDirectionForm {

    private String direccion;
    private String calle;
    private String ciudad;
    private String numero;
    private String codigoPostal;
    private String comunidadAutonoma;
    private String pais;
    private String provincia;
    private boolean isPrincipal;

}
