package com.dxvalley.creditscoring.userManager.user.dto;

import com.dxvalley.creditscoring.userManager.user.Users;

public class UserMapper {
    public static com.dxvalley.creditscoring.userManager.user.dto.UserResponse toUserResponse(Users user) {
        return com.dxvalley.creditscoring.userManager.user.dto.UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .organizationId(user.getOrganizationId())
                .role(user.getRole().getName())
                .lastLogin(user.getLastLogin())
                .enabled(user.isEnabled() ? "YES" : "NO")
                .status(user.getUserStatus().toString())
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

