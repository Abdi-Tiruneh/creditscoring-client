package com.dxvalley.creditscoring.userManager.user.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserResponse {
    private Long id;
    private String email;
    private String fullName;
    private String phoneNumber;
    private String organizationId;
    private String lastLogin;
    private String role;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
    private String enabled;
}
