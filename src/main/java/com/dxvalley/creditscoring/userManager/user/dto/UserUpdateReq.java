package com.dxvalley.creditscoring.userManager.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UserUpdateReq {

    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;
}
