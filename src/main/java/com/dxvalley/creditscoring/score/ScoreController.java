package com.dxvalley.creditscoring.score;

import com.dxvalley.creditscoring.score.dto.ScoreReq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/score")
@RequiredArgsConstructor
public class ScoreController {

    private final ScoreService scoreService;

    @PostMapping
    public ResponseEntity<?> getCustomerScore(@RequestBody @Valid ScoreReq scoreReq) {
        return scoreService.calculateScore(scoreReq);
    }

}
