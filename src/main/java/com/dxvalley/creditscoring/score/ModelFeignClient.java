package com.dxvalley.creditscoring.score;

import com.dxvalley.creditscoring.score.dto.ModelResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "model-client", url = "http://10.1.177.121:8888/api/v1/models")
public interface ModelFeignClient {
    @GetMapping("/{id}")
    ResponseEntity<ModelResponse> getModelById(@PathVariable Long id);
}
