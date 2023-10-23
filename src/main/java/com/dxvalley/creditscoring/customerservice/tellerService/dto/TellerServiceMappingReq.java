package com.dxvalley.creditscoring.customerservice.tellerService.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
public class TellerServiceMappingReq {

    @NotNull(message = "user ID is required")
    @Size(min = 1, message = "At least one user must be selected")
    private List<Long> userIds;

    @NotNull(message = "service IDs is required")
    @Size(min = 1, message = "At least one service must be selected")
    private List<Long> serviceIds;
}
