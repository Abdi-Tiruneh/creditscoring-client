package com.dxvalley.creditscoring.score;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;

@FeignClient(name = "score-service", url = "http://10.2.125.11:5000")
public interface ScoreFeignClient {
    @PostMapping(value = "/result", consumes = MediaType.APPLICATION_JSON_VALUE)
    ResponseEntity<String> calculateScore(String requestBody);
}
