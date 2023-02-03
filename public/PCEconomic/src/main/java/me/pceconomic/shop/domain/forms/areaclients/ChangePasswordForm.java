package me.pceconomic.shop.domain.forms.areaclients;

import lombok.Data;

public @Data class ChangePasswordForm {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
