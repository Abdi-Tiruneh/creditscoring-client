package com.dxvalley.creditscoring.userManager.user;

import com.dxvalley.creditscoring.customer.CustomerFeignClient;
import com.dxvalley.creditscoring.emailManager.EmailService;
import com.dxvalley.creditscoring.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.dxvalley.creditscoring.tokenManager.ConfirmationTokenService;
import com.dxvalley.creditscoring.userManager.role.Role;
import com.dxvalley.creditscoring.userManager.role.RoleService;
import com.dxvalley.creditscoring.userManager.user.dto.*;
import com.dxvalley.creditscoring.utils.ApiResponse;
import com.dxvalley.creditscoring.utils.CurrentLoggedInUser;
import com.dxvalley.creditscoring.utils.Status;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final RoleService roleService;
    private final UserUtils userUtils;
    private final CurrentLoggedInUser currentLoggedInUser;
    private final PasswordEncoder passwordEncoder;
    private final CustomerFeignClient customerFeignClient;
    private final ConfirmationTokenService confirmationTokenService;
    private final EmailService emailService;

    @Override
    @Transactional
    public com.dxvalley.creditscoring.userManager.user.dto.UserResponse register(UserRegistrationReq userReq) {
        if (userUtils.isEmailTaken(userReq.getEmail()))
            throw new ResourceAlreadyExistsException("Email is already taken.");

        if (userUtils.isPhoneNumberTaken(userReq.getPhoneNumber()))
            throw new ResourceAlreadyExistsException("Phone number is already taken.");

        Role role = roleService.getRoleByName(userReq.getRoleName().toUpperCase());
        Users loggedInUser = currentLoggedInUser.getUser();
        Users user = userUtils.createUser(userReq, loggedInUser, role);

        Users savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    @Transactional
    public com.dxvalley.creditscoring.userManager.user.dto.UserResponse registerOwner(OwnerRegistrationReq ownerReq) {
        customerFeignClient.activateCustomer(ownerReq.getCustomerId());

        if (userUtils.isEmailTaken(ownerReq.getEmail()))
            throw new ResourceAlreadyExistsException("Email is already taken");

        if (userUtils.isPhoneNumberTaken(ownerReq.getPhoneNumber()))
            throw new ResourceAlreadyExistsException("Phone number is already taken");

        Role role = roleService.getRoleByName("OWNER");
        Users user = userUtils.createUser(ownerReq, role);

        Users savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public List<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> getOrganizationUsers() {
        Users loggedInUser = currentLoggedInUser.getUser();
        List<Users> users = userRepository.findByOrganizationId(loggedInUser.getOrganizationId());
        return users.stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public com.dxvalley.creditscoring.userManager.user.dto.UserResponse editUser(UserUpdateReq updateReq) {
        Users user = currentLoggedInUser.getUser();

        if (updateReq.getFullName() != null) {
            user.setFullName(updateReq.getFullName());
        }

        // Update email if provided and different from the current email
        if (updateReq.getEmail() != null && !user.getUsername().equalsIgnoreCase(updateReq.getEmail())) {
            // Check if the new email is already taken
            if (userUtils.isEmailTaken(updateReq.getEmail())) {
                throw new ResourceAlreadyExistsException("Email is already taken");
            }
            user.setUsername(updateReq.getEmail());
        }

        // Update phone number if provided and different from the current phone number
        if (updateReq.getPhoneNumber() != null && !user.getPhoneNumber().equals(updateReq.getPhoneNumber())) {
            // Check if the new phone number is already taken
            if (userUtils.isPhoneNumberTaken(updateReq.getPhoneNumber())) {
                throw new ResourceAlreadyExistsException("Phone number is already taken");
            }
            user.setPhoneNumber(updateReq.getPhoneNumber());
        }

        user.setUpdatedBy(user.getFullName());
        Users savedUser = userRepository.save(user);

        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public com.dxvalley.creditscoring.userManager.user.dto.UserResponse activateBan(Long id) {
        Users user = userUtils.getById(id);

        if (user.getUserStatus() == Status.ACTIVE)
            user.setUserStatus(Status.BANNED);
        else
            user.setUserStatus(Status.ACTIVE);

        Users savedUser = userRepository.save(user);
        return UserMapper.toUserResponse(savedUser);
    }

    @Override
    public com.dxvalley.creditscoring.userManager.user.dto.UserResponse me() {
        Users user = currentLoggedInUser.getUser();
        return UserMapper.toUserResponse(user);
    }

    @Override
    public com.dxvalley.creditscoring.userManager.user.dto.UserResponse getById(Long userId) {
        Users user = userUtils.getById(userId);
        return UserMapper.toUserResponse(user);
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> delete(Long id) {
        Users user = userUtils.getById(id);

        // Set user attributes for deletion
        user.setDeleted(true);
        user.setDeletedAt(LocalDateTime.now().toString());
        user.setDeleteBy(currentLoggedInUser.getUser().getFullName());

        userRepository.save(user);
        return ApiResponse.success("User Deleted Successfully!");
    }

//
//    @Override
//    public List<UserDTO> getUsers() {
//        try {
//            List<Users> users = userRepository.findAll();
//            if (hasSysAdminRole()) {
//                List<UserDTO> result = users.stream().map(userDTOMapper).collect(Collectors.toList());
//                if (result.isEmpty())
//                    throw new ResourceNotFoundException("Currently, There is no User");
//
//                logger.info("SuperAdmin Retrieved {} users", result.size());
//                return result;
//            } else {
//                List<UserDTO> result = users.stream()
//                        .filter(user -> user.getRoles().stream()
//                                .noneMatch(role -> role.getRoleName().equals("SuperAdmin")))
//                        .map(userDTOMapper)
//                        .collect(Collectors.toList());
//
//                if (result.isEmpty())
//                    throw new ResourceNotFoundException("Currently, There is no User");
//
//                logger.info("Retrieved {} users", result.size());
//                return result;
//            }
//        } catch (DataAccessException ex) {
//            logger.error("Error retrieving Users: {}", ex.getMessage());
//            throw new RuntimeException("Error retrieving Users", ex);
//        }
//    }

//
//    // Utility method for this class
//    private boolean hasSysAdminRole() {
//        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//        return auth.getAuthorities()
//                .stream()
//                .anyMatch(grantedAuthority -> grantedAuthority.getAuthority().equals("SuperAdmin"));
//    }
//


}
