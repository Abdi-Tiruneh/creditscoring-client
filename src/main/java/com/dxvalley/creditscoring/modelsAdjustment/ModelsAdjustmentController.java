package com.dxvalley.creditscoring.modelsAdjustment;

import com.dxvalley.creditscoring.modelsAdjustment.dto.ModelsAdjustmentAddReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/modelsAdjustments")
@RequiredArgsConstructor
@Tag(name = "Models adjustment API. GOOD TO GO")
public class ModelsAdjustmentController {

    private final ModelsAdjustmentFeignClient modelsAdjustmentFeignClient;

    @GetMapping("/{organizationId}/{serviceId}")
    public ResponseEntity<?> myModelsAdjustments(@PathVariable String organizationId, @PathVariable Long serviceId) {
        return modelsAdjustmentFeignClient.getModelsAdjustment(organizationId, serviceId);
    }

    @PostMapping
    public ResponseEntity<?> createModelsAdjustments(@RequestBody List<ModelsAdjustmentAddReq> modelsAdjustmentAddReqs) {
        return modelsAdjustmentFeignClient.createModelsAdjustment(modelsAdjustmentAddReqs);
    }

}
