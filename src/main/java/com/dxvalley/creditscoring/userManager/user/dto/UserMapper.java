package com.dxvalley.creditscoring.userManager.user.dto;

import com.dxvalley.creditscoring.userManager.user.Users;

public class UserMapper {
    public static UserResponse toUserResponse(Users user) {
        return UserResponse.builder()
                .id(user.getId())
                .fullName(user.getFullName())
                .email(user.getUsername())
                .phoneNumber(user.getPhoneNumber())
                .organizationId(user.getOrganizationId())
                .role(user.getRole().getName())
                .lastLogin(user.getLastLogin())
                .enabled(user.isEnabled() ? "YES" : "NO")
                .createdBy(user.getCreatedBy())
                .updatedBy(user.getUpdatedBy())
                .createdAt(user.getCreatedAt())
                .updatedAt(user.getUpdatedAt())
                .build();
    }
}

