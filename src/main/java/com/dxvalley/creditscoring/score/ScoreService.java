package com.dxvalley.creditscoring.score;

import com.dxvalley.creditscoring.exceptions.customExceptions.ServiceUnavailableException;
import com.dxvalley.creditscoring.score.dto.ModelResponse;
import com.dxvalley.creditscoring.score.dto.ScoreReq;
import com.dxvalley.creditscoring.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ScoreFeignClient scoreFeignClient;
    private final ModelFeignClient modelFeignClient;

    public ResponseEntity<?> calculateScore(ScoreReq scoreReq) {
        ModelResponse modelResponse = modelFeignClient.getModelById(scoreReq.getModelId()).getBody();

        if (modelResponse.getModelStatus() == Status.BLOCKED)
            throw new ServiceUnavailableException();

        String transactions = "{\"total_0\":123005, \"Average_0\": 1237804, \"freq_0\":11131005, \"total_1\":1116224, \"Average_1\":1000001, \n" +
                "       \"freq_1\":1012305, \"total_2\":1019004, \"Average_2\":101, \"freq_2\":1304005, \"total_3\":1304004, \"Average_3\":101, \n" +
                "       \"freq_3\":874004, \"total_4\":874003, \"Average_4\":101, \"freq_4\":1809005, \"total_5\":1809004, \"Average_5\":101, \n" +
                "       \"freq_5\":132505, \"total_6\":132504, \"Average_6\":1000001, \"freq_6\":10000001, \"total_7\":792004, \"Average_7\":8877803, \n" +
                "       \"freq_7\":900000 }";

        return scoreFeignClient.calculateScore(transactions);
    }

}
