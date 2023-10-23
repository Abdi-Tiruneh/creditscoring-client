package com.dxvalley.creditscoring.customerservice.tellerService;

import com.dxvalley.creditscoring.customerservice.tellerService.dto.TellerServiceMappingReq;
import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.userManager.user.dto.UserResponse;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface TellerServiceMappingService {

    List<TellerServiceMapping> assignServiceToTeller(TellerServiceMappingReq tellerServiceMappingReq);
    List<UserResponse> getServiceTellers(Long id);
}
