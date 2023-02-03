package me.pceconomic.shop.domain.forms.areaclients;

import jakarta.validation.constraints.Email;
import lombok.Data;

public @Data class ChangeEmailForm {

    @Email
    private String newEmail;

    @Email
    private String confirmNewEmail;

}
