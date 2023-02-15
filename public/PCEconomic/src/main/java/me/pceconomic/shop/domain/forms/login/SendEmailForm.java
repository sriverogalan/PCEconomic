package me.pceconomic.shop.domain.forms.login;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

public @Data class SendEmailForm {

    @Email(message = "{incorrect.email}")
    @NotNull(message = "{incorrect.email}")
    @NotEmpty(message = "{incorrect.email}")
    private String email;

}
