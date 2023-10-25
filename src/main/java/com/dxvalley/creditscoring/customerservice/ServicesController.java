package com.dxvalley.creditscoring.customerservice;

import com.dxvalley.creditscoring.businessRule.BusinessRule;
import com.dxvalley.creditscoring.businessRule.BusinessRuleService;
import com.dxvalley.creditscoring.customerservice.tellerService.TellerServiceMapping;
import com.dxvalley.creditscoring.customerservice.tellerService.TellerServiceMappingService;
import com.dxvalley.creditscoring.customerservice.tellerService.dto.TellerServiceMappingReq;
import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.userManager.user.dto.UserResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/services")
@RequiredArgsConstructor
@Tag(name = "Services API. GOOD TO GO")
public class ServicesController {

    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final ServicesService servicesService;
    private final TellerServiceMappingService tellerServiceMappingService;
    private final BusinessRuleService businessRuleService;

    @GetMapping
    public ResponseEntity<?> myServices() {
        return servicesService.myServices();
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getService(@PathVariable Long id) {
        return customerServiceFeignClient.getService(id);
    }

    @GetMapping("/{id}/tellers")    
    public ResponseEntity<List<UserResponse>> getServiceTellers(@PathVariable Long id) {
        List<UserResponse> tellers = tellerServiceMappingService.getServiceTellers(id);
        return ResponseEntity.ok(tellers);
    }

    @PutMapping("/{id}/toggleStatus")
    public ResponseEntity<?> toggleServicesStatus(@PathVariable Long id) {
        return customerServiceFeignClient.toggleServicesStatus(id);
    }

    @PostMapping("/assignToTeller")
    ResponseEntity<List<TellerServiceMapping>> assignServiceToTeller(@RequestBody @Valid TellerServiceMappingReq tellerServiceMappingReq) {
        List<TellerServiceMapping> tellerServiceMappings = tellerServiceMappingService.assignServiceToTeller(tellerServiceMappingReq);
        return ResponseEntity.ok(tellerServiceMappings);
    }

    @GetMapping("/{serviceId}/businessRules")
    public ResponseEntity<List<BusinessRule>> getServiceBusinessRules(@PathVariable Long serviceId) {
        return ResponseEntity.ok(businessRuleService.getServiceBusinessRules(serviceId));
    }
}
