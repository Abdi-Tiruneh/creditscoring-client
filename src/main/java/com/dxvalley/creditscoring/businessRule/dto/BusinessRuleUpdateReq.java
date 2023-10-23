package com.dxvalley.creditscoring.businessRule.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class BusinessRuleUpdateReq {

    private String fullName;

    @Email(message = "Invalid email format")
    private String email;

    private String phoneNumber;
}
