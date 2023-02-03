package me.pceconomic.shop.domain.forms;

import lombok.Data;

public @Data class ChangeNameForm {

    private String oldName;
    private String newName;
    private String confirmNewName;

}
