package me.pceconomic.shop.domain.forms.areaclients;

import lombok.Data;

public @Data class ChangeTelephoneForm {

    private String oldTelephone;
    private String newTelephone;
    private String confirmNewTelephone;

}
