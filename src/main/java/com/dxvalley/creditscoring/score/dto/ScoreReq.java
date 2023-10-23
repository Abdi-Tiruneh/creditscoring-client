package com.dxvalley.creditscoring.score.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ScoreReq {
    @NotNull(message = "model Id is required")
    private Long modelId;

    @NotBlank(message = "accountNumber is required")
    private String accountNumber;
}
