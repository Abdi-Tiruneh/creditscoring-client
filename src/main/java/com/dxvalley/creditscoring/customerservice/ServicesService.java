package com.dxvalley.creditscoring.customerservice;

import com.dxvalley.creditscoring.customerservice.tellerService.TellerServiceMapping;
import com.dxvalley.creditscoring.customerservice.tellerService.TellerServiceMappingRepository;
import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.utils.CurrentLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ServicesService {

    private final CustomerServiceFeignClient customerServiceFeignClient;
    private final CurrentLoggedInUser currentLoggedInUser;
    private final TellerServiceMappingRepository tellerServiceMappingRepository;

    public ResponseEntity<?> myServices() {

        Users user = currentLoggedInUser.getUser();

        if (user.getRole().getName().equals("TELLER")) {
            TellerServiceMapping tellerServiceMapping = tellerServiceMappingRepository.findByUserId(user.getId()).get();
            return customerServiceFeignClient.getServicesById(tellerServiceMapping.getServiceIds());
        }

        return customerServiceFeignClient.getCustomerServices(user.getOrganizationId());
    }

}
