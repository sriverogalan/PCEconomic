package me.pceconomic.shop.domain.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public @Data class RegisterForm {

    @NotNull(message = "{incorrect.name}")
    @NotEmpty(message = "{incorrect.name}")
    private String name;

    @NotNull(message = "{incorrect.surnames}")
    @NotEmpty(message = "{incorrect.surnames}")
    private String surname1;

    private String surname2;

    @Email(message = "{incorrect.email}")
    @NotNull(message = "{incorrect.email}")
    @NotEmpty(message = "{incorrect.email}")
    private String email;

    @NotNull(message = "{blank.password}")
    @NotEmpty(message = "{blank.password}")
    private String password;

    @NotNull(message = "{incorrect.password.confirm}")
    @NotEmpty(message = "{incorrect.password.confirm}")
    private String confirmPassword;

    @NotNull(message = "{incorrect.dni}")
    @NotEmpty(message = "{incorrect.dni}")
    @Pattern(regexp = "^[0-9]{8}[a-zA-Z]$", message = "{incorrect.dni}")
    private String dni;

    @NotNull(message = "{incorrect.phone}")
    @NotEmpty(message = "{incorrect.phone}")
    @Pattern(regexp = "^[0-9]{9}$", message = "{incorrect.phone}")
    private String telefon;


}
