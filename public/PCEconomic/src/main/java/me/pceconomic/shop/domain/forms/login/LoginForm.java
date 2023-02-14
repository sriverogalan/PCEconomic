package me.pceconomic.shop.domain.forms.login;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

public @Data class LoginForm {

    @NotNull(message = "{incorrect.email}")
    private String email;

    @NotNull(message = "{blank.password}")
    private String password;

}
