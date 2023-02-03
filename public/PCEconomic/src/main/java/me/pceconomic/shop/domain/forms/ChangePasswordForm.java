package me.pceconomic.shop.domain.forms;

import lombok.Data;

public @Data class ChangePasswordForm {

    private String oldPassword;
    private String newPassword;
    private String confirmPassword;

}
