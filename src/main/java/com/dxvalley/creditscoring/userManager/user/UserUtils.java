package com.dxvalley.creditscoring.userManager.user;

import com.dxvalley.creditscoring.exceptions.customExceptions.BadRequestException;
import com.dxvalley.creditscoring.exceptions.customExceptions.ResourceNotFoundException;
import com.dxvalley.creditscoring.userManager.role.Role;
import com.dxvalley.creditscoring.userManager.user.dto.OwnerRegistrationReq;
import com.dxvalley.creditscoring.userManager.user.dto.UserRegistrationReq;
import com.dxvalley.creditscoring.userManager.user.dto.UserResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserUtils {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public Users createUser(UserRegistrationReq userReq, Users loggedInUser, Role role) {
        return Users.builder()
                .username(userReq.getEmail())
                .password(passwordEncoder.encode(userReq.getPassword()))
                .fullName(userReq.getFullName())
                .phoneNumber(userReq.getPhoneNumber())
                .organizationId(loggedInUser.getOrganizationId())
                .role(role)
                .createdBy(loggedInUser.getFullName())
                .isEnabled(true)
                .build();
    }

    public Users createUser(OwnerRegistrationReq ownerReq, Role role) {
        return Users.builder()
                .username(ownerReq.getEmail())
                .password(passwordEncoder.encode(ownerReq.getPassword()))
                .fullName(ownerReq.getFullName())
                .phoneNumber(ownerReq.getPhoneNumber())
                .organizationId("getOrganizationId")
                .role(role)
                .createdBy("System")
                .isEnabled(true)
                .build();
    }

    public boolean isEmailTaken(String email) {
        return userRepository.findByUsername(email).isPresent();
    }

    public boolean isPhoneNumberTaken(String phoneNumber) {
        return userRepository.findByPhoneNumber(phoneNumber).isPresent();
    }

    public void validateOldPassword(Users user, String oldPassword) {
        boolean isPasswordMatch = passwordEncoder.matches(oldPassword, user.getPassword());
        if (!isPasswordMatch) {
            throw new BadRequestException("Incorrect old Password!");
        }
    }

    public Users getById(Long userId) {
        return userRepository.findById(userId)
                .orElseThrow(() -> new ResourceNotFoundException("User not found"));
    }

}
