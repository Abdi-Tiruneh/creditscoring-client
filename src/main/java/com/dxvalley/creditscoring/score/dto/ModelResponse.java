package com.dxvalley.creditscoring.score.dto;

import com.dxvalley.creditscoring.utils.Status;
import lombok.Data;

@Data
public class ModelResponse {
    private Long id;

    private String name;

    private String path;

    private String description;

    private Status modelStatus;

    private String addedBy;

    private String addedAt;

    private String updatedBy;

    private String updatedAt;
}
