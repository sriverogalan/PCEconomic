package me.pceconomic.shop.domain.forms;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

public @Data class RegisterForm {

    @NotNull
    @NotEmpty
    private String name;

    @NotNull
    @NotEmpty
    private String surname1;

    private String surname2;

    @Email
    @NotNull
    @NotEmpty
    private String email;

    @NotNull
    @NotEmpty
    private String password;

    @NotNull
    @NotEmpty
    private String confirmPassword;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[0-9]{8}[A-Z]$")
    private String dni;

    @NotNull
    @NotEmpty
    @Pattern(regexp = "^[0-9]{9}$")
    private String telefon;


}
