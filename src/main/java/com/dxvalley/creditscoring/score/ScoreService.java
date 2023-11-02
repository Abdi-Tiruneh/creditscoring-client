package com.dxvalley.creditscoring.score;

import com.dxvalley.creditscoring.customerservice.CustomerServiceFeignClient;
import com.dxvalley.creditscoring.exceptions.customExceptions.ServiceUnavailableException;
import com.dxvalley.creditscoring.score.dto.CustomerInformationResponse;
import com.dxvalley.creditscoring.score.dto.ModelResponse;
import com.dxvalley.creditscoring.score.dto.ScoreReq;
import com.dxvalley.creditscoring.score.dto.ScoreResponse;
import com.dxvalley.creditscoring.score.dto.CustomerServiceResponse;
import com.dxvalley.creditscoring.utils.Status;

import lombok.Data;
import lombok.RequiredArgsConstructor;

import org.springframework.http.HttpStatus;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
@RequiredArgsConstructor
public class ScoreService {
    private final ModelFeignClient modelFeignClient;
    private final CustomerServiceFeignClient customerServiceFeignClient;

    public ResponseEntity<?> calculateScore(ScoreReq scoreReq) {

        // get models in specific service
        // ResponseEntity<CustomerServiceResponse> customerServiceResponse = customerServiceFeignClient.getService(scoreReq.getModelId());

        // apply adjustment to models response and give out sinlge output with their business rule range
        // 
        ModelResponse modelResponse = modelFeignClient.getModelById(scoreReq.getModelId()).getBody();
        String modelUrl = modelResponse.getPath();
        String url = "http://10.1.245.150:7081/v1/cbo/";

        if (modelResponse.getModelStatus() == Status.BLOCKED)
            throw new ServiceUnavailableException();
        
        // get account, date, and modelId then request cb for data and feed the data to the model path and return the score 
        // response for frontend
        try {
            RestTemplate restTemplate = new RestTemplate();

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);

            String requestBody = "{\"AccountstatementForAGivenDateRequest\":{\"ESBHeader\":{\"serviceCode\":\"570000\",\"channel\":\"USSD\",\"Service_name\":\"AccountStatement\",\"Message_Id\":\"6255726662\"},\"EMMTMINISTMTDATEType\":[{\"columnName\":\"ACCOUNT\","
                    +
                    "\"criteriaValue\":\"" + scoreReq.getAccountNumber()
                    + "\",\"operand\":\"EQ\"},{\"columnName\":\"BOOKING.DATE\",\"criteriaValue\":\""
                    + "20230101 20230401" + "\",\"operand\":\"RG\"}]}}";

            System.out.println("############### date range "+generateDateRange());

            HttpEntity<String> request = new HttpEntity<String>(requestBody, headers);

            ResponseEntity<String> accountStatementResponse = restTemplate.exchange(url,
                    HttpMethod.POST, request,
                    String.class);


            String scoreRequestBody = accountStatementResponse.getBody().toString();

            HttpEntity<String> scoreRequest = new HttpEntity<String>(scoreRequestBody, headers);
            
            System.out.println("############### date range "+accountStatementResponse);
            
            ResponseEntity<ScoreResponse> scoreResponse = restTemplate.exchange(modelUrl,
                    HttpMethod.POST, scoreRequest,
                    ScoreResponse.class);

            // customer info
            String customerInfoRequestBody = "{\"CustomerInfoRequest\":{\"ESBHeader\":{\"serviceCode\":\"040000\",\"channel\":\"USSD\",\"Service_name\":\"customerInfo\",\"Message_Id\":\"Mmr2qyutr82729\"},\"CusomerInfo\":{\"AccountId\":\""+
                                            scoreReq.getAccountNumber() +"\"}}}";

            HttpEntity<String> customerInfoRequest = new HttpEntity<String>(customerInfoRequestBody, headers);

            ResponseEntity<CustomerInformationResponse> customerInfoResponse = restTemplate.exchange(url,
                    HttpMethod.POST, customerInfoRequest,
                    CustomerInformationResponse.class);
                    
            System.out.println("############### "+customerInfoResponse.getBody());

            CombinedResponse combinedResponse = new CombinedResponse(scoreResponse.getBody(), customerInfoResponse.getBody());

            return new ResponseEntity<>(combinedResponse, HttpStatus.OK);

        } catch (Exception e) {
            e.printStackTrace();
            
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }

    }


     public String generateDateRange() {
        // Get today's date
        LocalDate today = LocalDate.now();

        // Subtract one month from today's date
        LocalDate startDate = today.minusMonths(1);

        // Define a date format
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        // Format the dates as strings
        String startDateStr = startDate.format(formatter);
        String endDateStr = today.minusDays(1).format(formatter);

        return startDateStr + " " + endDateStr;
    }

}


@Data
class CombinedResponse{
    private ScoreResponse scoreResponse;
    private CustomerInformationResponse customerInformationResponse;
    public CombinedResponse(ScoreResponse scoreResponse, CustomerInformationResponse customerInformationResponse) {
        this.customerInformationResponse = customerInformationResponse;
        this.scoreResponse = scoreResponse;
    }
}

