package com.dxvalley.creditscoring.userManager.account;

import com.dxvalley.creditscoring.emailManager.EmailBuilder;
import com.dxvalley.creditscoring.emailManager.EmailService;
import com.dxvalley.creditscoring.tokenManager.ConfirmationToken;
import com.dxvalley.creditscoring.tokenManager.ConfirmationTokenService;
import com.dxvalley.creditscoring.userManager.user.UserRepository;
import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.userManager.user.UserUtils;
import com.dxvalley.creditscoring.userManager.user.dto.ChangePassword;
import com.dxvalley.creditscoring.userManager.user.dto.ResetPassword;
import com.dxvalley.creditscoring.utils.ApiResponse;
import com.dxvalley.creditscoring.utils.CurrentLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {

    private final UserRepository userRepository;
    private final UserUtils userUtils;
    private final CurrentLoggedInUser currentLoggedInUser;
    private final PasswordEncoder passwordEncoder;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> changePassword(ChangePassword temp) {
        Users user = currentLoggedInUser.getUser();

        userUtils.validateOldPassword(user, temp.getOldPassword());

        user.setPassword(passwordEncoder.encode(temp.getNewPassword()));
        user.setUpdatedBy(user.getFullName());

        userRepository.save(user);

        return ApiResponse.success("Password Changed Successfully!");
    }

    @Override
    public ResponseEntity<ApiResponse> forgotPassword(String email) {
        Users user = userUtils.getByUsername(email);
        String code = UUID.randomUUID().toString();
        String link = "http://10.100.51.60/resetPassword/" + code;
        emailService.send(user.getUsername(), EmailBuilder.emailBuilderForPasswordReset(user.getFullName(), link), "Reset your password");
        confirmationTokenService.addToken(user, code, 30);
        return ApiResponse.success("Please check your email");
    }

    @Transactional
    @Override
    public ResponseEntity<ApiResponse> resetPassword(ResetPassword resetPassword) {
        ConfirmationToken confirmationToken = confirmationTokenService.checkTokenExpiration(resetPassword.getToken());
        String username = confirmationToken.getUser().getUsername();
        Users user = userUtils.getByUsername(username);

        user.setPassword(passwordEncoder.encode(resetPassword.getPassword()));
        userRepository.save(user);
        confirmationTokenService.delete(confirmationToken);

        return ApiResponse.success("Hooray! Your password has been successfully reset.");
    }
}
