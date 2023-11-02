package com.dxvalley.creditscoring.score.dto;

import java.util.List;

import com.dxvalley.creditscoring.utils.Status;
import lombok.Data;

@Data
public class CustomerServiceResponse {
    private Long id;

    private String name;

    private String description;

    private Status serviceStatus;

    private String addedBy;

    private String addedAt;

    private String updatedBy;

    private String updatedAt;

    private List<ModelResponse> models;
}


