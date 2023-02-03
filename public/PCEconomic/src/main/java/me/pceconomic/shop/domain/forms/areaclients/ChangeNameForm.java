package me.pceconomic.shop.domain.forms.areaclients;

import lombok.Data;

public @Data class ChangeNameForm {

    private String newName;
    private String confirmNewName;

}
