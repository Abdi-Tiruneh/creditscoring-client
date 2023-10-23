package com.dxvalley.creditscoring.businessRule.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Data;

@Data
public class BusinessRuleReq {

    @NotNull(message = "minScore is required")
    @Min(value = 0, message = "minScore must be a positive number")
    private Integer minScore;

    @NotNull(message = "maxScore is required")
    @Min(value = 1, message = "maxScore must be greater than 0")
    private Integer maxScore;

    @NotNull(message = "amountAllowed is required")
    @Positive(message = "amountAllowed must be a positive number")
    private Double amountAllowed;

    @NotNull(message = "services is required")
    private Long serviceId;

    private String remark;
    private String xCode;
}
