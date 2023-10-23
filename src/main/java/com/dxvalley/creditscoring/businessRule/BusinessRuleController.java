package com.dxvalley.creditscoring.businessRule;

import com.dxvalley.creditscoring.businessRule.dto.BusinessRuleReq;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/business-rules")
@RequiredArgsConstructor
@Tag(name = "Business Rule API. GOOD TO GO")
public class BusinessRuleController {

    private final BusinessRuleService businessRuleService;

    @PostMapping
    public ResponseEntity<List<BusinessRule>> addBusinessRule(@RequestBody @Valid List<BusinessRuleReq> businessRuleReq) {
        List<BusinessRule> businessRules = businessRuleService.addBusinessRule(businessRuleReq);
        return ResponseEntity.status(HttpStatus.CREATED).body(businessRules);
    }

    @PutMapping
    ResponseEntity<List<BusinessRule>> editBusinessRule(@RequestBody List<BusinessRule> businessRules) {
        return ResponseEntity.ok(businessRuleService.updateBusinessRule(businessRules));
    }

//    @GetMapping("/{businessRuleId}")
//    public ResponseEntity<?> getBusinessRuleById(@PathVariable Long businessRuleId) {
//        return new ResponseEntity<>(businessRuleService.getBusinessRuleByBusinessRuleId(businessRuleId), HttpStatus.OK);
//    }

//    @DeleteMapping("/{businessRuleId}")
//    ResponseEntity<?> deletebusinessRule(@PathVariable Long businessRuleId) throws ResourceNotFoundException {
//        businessRuleService.delete(businessRuleId);
//        ApiResponse<?> response = new ApiResponse<>(200, "businessRule successfully deleted!", null);
//        return new ResponseEntity<>(response, HttpStatus.OK);
//    }

}
