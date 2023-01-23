package me.pceconomic.shop.domain.forms;

import lombok.Data;

public @Data class RegisterForm {

    private String name;
    private String surname1;
    private String surname2;
    private String password;
    private String confirmPassword;


}
