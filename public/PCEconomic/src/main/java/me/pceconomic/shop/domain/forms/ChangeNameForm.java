package me.pceconomic.shop.domain.forms;

import lombok.Getter;

@Getter
public class ChangeNameForm {

    private String oldName;
    private String newName;
    private String confirmNewName;

}
