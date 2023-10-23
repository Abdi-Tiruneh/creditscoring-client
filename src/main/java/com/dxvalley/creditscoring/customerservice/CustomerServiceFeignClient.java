package com.dxvalley.creditscoring.customerservice;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(name = "CustomerService", url = "http://localhost:8888/api/v1/services")
public interface CustomerServiceFeignClient {

    @GetMapping("/customer/{organizationId}")
    ResponseEntity<?> getCustomerServices(@PathVariable String organizationId);

    @GetMapping("/by-ids")
    ResponseEntity<?> getServicesById(@RequestParam List<Long> serviceIds);

    @PutMapping("/{id}/toggleStatus")
    ResponseEntity<?> toggleServicesStatus(@PathVariable Long id);

    @GetMapping("/{id}")
    ResponseEntity<?> getService(@PathVariable Long id);
}