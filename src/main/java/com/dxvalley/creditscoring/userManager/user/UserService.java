package com.dxvalley.creditscoring.userManager.user;

import com.dxvalley.creditscoring.userManager.user.dto.*;
import com.dxvalley.creditscoring.utils.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface UserService {

    Users register(UserRegistrationReq userReq);

    Users registerOwner(OwnerRegistrationReq ownerReq);

    List<UserResponse> getOrganizationUsers();

    UserResponse me();
    UserResponse getById(Long userId);

    UserResponse editUser(UserUpdateReq updateReq);

    ResponseEntity<ApiResponse> changePassword(ChangePassword temp);

    ResponseEntity<ApiResponse> delete(Long id);

//    ApiResponse<UserDTO> createUser(UserRequest tempUser);
//
//    ApiResponse<UserDTO> blockUser(UserRequest tempUser, Long userId);
//
//    UserDTO getUserById(Long userId);
//
//    UserDTO getUserByUsername(String username);
//
//    ApiResponse<UserDTO> updateUser(Users tempUser);
//
//    List<UserDTO> getUsers();
//
//    List<UserDTO> getTellers(Long customerId);
//
//    List<UserDTO> getAdmins(Long customerId);
//
//    ApiResponse<?> changePassword(String username, ChangePassword temp);
//
//    ApiResponse<?> forgotPassword(String username);
//
//    void delete(Long userId);
//
//    // ApiResponse resetPassword(ResetPassword resetPassword);

}
