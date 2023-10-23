package com.dxvalley.creditscoring.tokenManager;

import com.dxvalley.creditscoring.userManager.user.Users;
import org.springframework.stereotype.Service;

@Service
public interface ConfirmationTokenService {
    ConfirmationToken addToken(Users user, String token, int expirationTimeInMinutes);

    ConfirmationToken checkTokenExpiration(String token);

    void delete(ConfirmationToken confirmationToken);
}
