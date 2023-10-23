package com.dxvalley.creditscoring.emailManager;

import com.dxvalley.creditscoring.utils.ApiResponse;

import java.util.concurrent.CompletableFuture;

public interface EmailService {

    CompletableFuture<ApiResponse> send(String recipientEmail, String emailBody, String emailSubject);
}
