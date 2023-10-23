package com.dxvalley.creditscoring.userManager.account;

import com.dxvalley.creditscoring.userManager.user.dto.ChangePassword;
import com.dxvalley.creditscoring.userManager.user.dto.ResetPassword;
import com.dxvalley.creditscoring.utils.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/account")
@Tag(name = "Account API. GOOD TO GO")
public class AccountController {

    private final AccountService accountService;

    @PutMapping("/password/change")
    public ResponseEntity<ApiResponse> changePassword(@RequestBody @Valid ChangePassword changePassword) {
        return accountService.changePassword(changePassword);
    }

    @PostMapping("/password/forget/{email}")
    public ResponseEntity<ApiResponse> forgotPassword(@PathVariable String email) {
        return accountService.forgotPassword(email);
    }

    @PutMapping("/password/reset")
    public ResponseEntity<ApiResponse> resetPassword(@RequestBody @Valid ResetPassword resetPassword) {
        return accountService.resetPassword(resetPassword);
    }
}
