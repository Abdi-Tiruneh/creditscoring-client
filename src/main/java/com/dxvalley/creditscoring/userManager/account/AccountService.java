package com.dxvalley.creditscoring.userManager.account;

import com.dxvalley.creditscoring.userManager.user.dto.ChangePassword;
import com.dxvalley.creditscoring.userManager.user.dto.ResetPassword;
import com.dxvalley.creditscoring.utils.ApiResponse;
import org.springframework.http.ResponseEntity;

public interface AccountService {

    ResponseEntity<ApiResponse> changePassword(ChangePassword temp);

    ResponseEntity<ApiResponse> forgotPassword(String email);

    ResponseEntity<ApiResponse> resetPassword(ResetPassword resetPassword);
}
