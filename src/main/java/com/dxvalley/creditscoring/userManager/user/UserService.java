package com.dxvalley.creditscoring.userManager.user;

import com.dxvalley.creditscoring.userManager.user.dto.OwnerRegistrationReq;
import com.dxvalley.creditscoring.userManager.user.dto.UserRegistrationReq;
import com.dxvalley.creditscoring.userManager.user.dto.UserResponse;
import com.dxvalley.creditscoring.userManager.user.dto.UserUpdateReq;
import com.dxvalley.creditscoring.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    UserResponse register(UserRegistrationReq userReq);

    UserResponse registerOwner(OwnerRegistrationReq ownerReq);

    List<UserResponse> getOrganizationUsers();

    UserResponse me();

    UserResponse getById(Long userId);

    UserResponse editUser(UserUpdateReq updateReq);

    UserResponse activateBan(Long id);

    ResponseEntity<ApiResponse> delete(Long id);
}
