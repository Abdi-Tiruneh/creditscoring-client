package com.dxvalley.creditscoring.tokenManager;

import com.dxvalley.creditscoring.exceptions.customExceptions.BadRequestException;
import com.dxvalley.creditscoring.userManager.user.Users;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@Slf4j
@RequiredArgsConstructor
public class ConfirmationTokenServiceImpl implements ConfirmationTokenService {
    private final ConfirmationTokenRepository confirmationTokenRepository;

    @Override
    public ConfirmationToken checkTokenExpiration(String token) {
        ConfirmationToken confirmationToken = confirmationTokenRepository.findByToken(token)
                .orElseThrow(() -> new BadRequestException("Invalid Token"));

        LocalDateTime expiredAt = LocalDateTime.parse(confirmationToken.getExpiresAt());
        if (expiredAt.isBefore(LocalDateTime.now())) {
            confirmationTokenRepository.delete(confirmationToken);
            throw new BadRequestException("The token has expired.");
        } else {
            return confirmationToken;
        }
    }

    @Override
    public ConfirmationToken addToken(Users user, String token, int expirationTimeInMinutes) {
        ConfirmationToken confirmationToken = new ConfirmationToken();
        confirmationToken.setUser(user);
        confirmationToken.setToken(token);
        confirmationToken.setCreatedAt(LocalDateTime.now().toString());
        confirmationToken.setExpiresAt(LocalDateTime.now().plusMinutes(expirationTimeInMinutes).toString());
        return confirmationTokenRepository.save(confirmationToken);
    }

    @Override
    public void delete(ConfirmationToken confirmationToken) {
        confirmationTokenRepository.delete(confirmationToken);
    }
}
