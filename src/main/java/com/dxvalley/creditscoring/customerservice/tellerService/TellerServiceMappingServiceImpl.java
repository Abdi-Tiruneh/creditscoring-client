package com.dxvalley.creditscoring.customerservice.tellerService;

import com.dxvalley.creditscoring.customerservice.tellerService.dto.TellerServiceMappingReq;
import com.dxvalley.creditscoring.exceptions.customExceptions.BadRequestException;
import com.dxvalley.creditscoring.exceptions.customExceptions.ResourceNotFoundException;
import com.dxvalley.creditscoring.userManager.user.UserUtils;
import com.dxvalley.creditscoring.userManager.user.Users;
import com.dxvalley.creditscoring.userManager.user.dto.UserMapper;
import com.dxvalley.creditscoring.userManager.user.dto.UserResponse;
import com.dxvalley.creditscoring.utils.CurrentLoggedInUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TellerServiceMappingServiceImpl implements TellerServiceMappingService {

    private final TellerServiceMappingRepository tellerServiceMappingRepository;
    private final UserUtils userUtils;
    private final CurrentLoggedInUser currentLoggedInUser;

    @Override
    public List<TellerServiceMapping> assignServiceToTeller(TellerServiceMappingReq tellerServiceMappingReq) {
        List<Users> users = userUtils.findAllById(tellerServiceMappingReq.getUserIds());

        // Check if all users are tellers
        if (users.stream().anyMatch(user -> !user.getRole().getName().equals("TELLER"))) {
            throw new BadRequestException("Only tellers have access to specific services. Other users can view all services.");
        }

        List<TellerServiceMapping> tellerServiceMappings = users.stream().map(user -> {
            TellerServiceMapping tellerServiceMapping = tellerServiceMappingRepository
                    .findByUserId(user.getId())
                    .orElseGet(TellerServiceMapping::new);

            tellerServiceMapping.setUser(user);
            tellerServiceMapping.setServiceIds(tellerServiceMappingReq.getServiceIds());
            tellerServiceMapping.setCreatedBy(currentLoggedInUser.getUser().getFullName());

            return tellerServiceMapping;
        }).toList();

        return tellerServiceMappingRepository.saveAll(tellerServiceMappings);
    }

    @Override
    public List<UserResponse> getServiceTellers(Long serviceId) {
        List<TellerServiceMapping> allMappings = tellerServiceMappingRepository.findAll();

        // Filter mappings by serviceId.
        List<TellerServiceMapping> tellerServiceMappings = allMappings.stream()
                .filter(mapping -> mapping.getServiceIds().contains(serviceId))
                .toList();

        if (tellerServiceMappings.isEmpty())
            throw new ResourceNotFoundException("No teller is assigned to this service.");


        // Map TellerServiceMapping to UserResponse.
        return tellerServiceMappings.stream()
                .map(tellerServiceMapping -> UserMapper.toUserResponse(tellerServiceMapping.getUser()))
                .toList();

    }

}
