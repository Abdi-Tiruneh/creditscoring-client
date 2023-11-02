package com.dxvalley.creditscoring.modelsAdjustment.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class ModelsAdjustmentAddReq {

    @NotBlank(message = "Name is required")
    private String modelName;

    @Size(min = 1, message = "model must be selected")
    private Long modelId;

    @Size(min = 1, message = "service must be selected")
    private Long serviceId;

    @NotNull(message = "Organization ID is required")
    @Positive(message = "Organization ID must be a positive number")
    private String organizationId;

    @NotBlank(message = "model contribution is required")
    private String modelContribution;

}
