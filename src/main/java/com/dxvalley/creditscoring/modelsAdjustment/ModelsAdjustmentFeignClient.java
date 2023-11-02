package com.dxvalley.creditscoring.modelsAdjustment;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

import com.dxvalley.creditscoring.modelsAdjustment.dto.ModelsAdjustmentAddReq;

import io.swagger.v3.oas.annotations.parameters.RequestBody;

import java.util.List;

@FeignClient(name = "models-adjustment", url = "http://localhost:8888/api/v1/modelsAdjustment")
public interface ModelsAdjustmentFeignClient {

    @GetMapping("/{organizationId}/{serviceId}")
    ResponseEntity<?> getModelsAdjustment(@PathVariable String organizationId, @PathVariable Long serviceId);

    @PostMapping
    ResponseEntity<?> createModelsAdjustment(@RequestBody List<ModelsAdjustmentAddReq> modelsAdjustmentAddReqs);

}