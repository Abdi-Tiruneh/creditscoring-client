package com.dxvalley.creditscoring.userManager.user;

import com.dxvalley.creditscoring.exceptions.customExceptions.ResourceAlreadyExistsException;
import com.dxvalley.creditscoring.userManager.role.Role;
import com.dxvalley.creditscoring.userManager.role.RoleService;
import com.dxvalley.creditscoring.userManager.user.dto.*;
import com.dxvalley.creditscoring.utils.ApiResponse;
import com.dxvalley.creditscoring.utils.CurrentLoggedInUser;
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

    @Override
    @Transactional
    public Users register(UserRegistrationReq userReq) {
        if (userUtils.isEmailTaken(userReq.getEmail()))
            throw new ResourceAlreadyExistsException("Email is already taken");

        if (userUtils.isPhoneNumberTaken(userReq.getPhoneNumber()))
            throw new ResourceAlreadyExistsException("Phone number is already taken");

        Role role = roleService.getRoleByName(userReq.getRoleName().toUpperCase());
        Users loggedInUser = currentLoggedInUser.getUser();
        Users user = userUtils.createUser(userReq, loggedInUser, role);

        return userRepository.save(user);
    }

    @Override
    @Transactional
    public Users registerOwner(OwnerRegistrationReq ownerReq) {
        if (userUtils.isEmailTaken(ownerReq.getEmail()))
            throw new ResourceAlreadyExistsException("Email is already taken");

        if (userUtils.isPhoneNumberTaken(ownerReq.getPhoneNumber()))
            throw new ResourceAlreadyExistsException("Phone number is already taken");

        Role role = roleService.getRoleByName("OWNER");
        Users user = userUtils.createUser(ownerReq, role);
        return userRepository.save(user);
    }

    @Override
    public List<UserResponse> getOrganizationUsers() {
        Users loggedInUser = currentLoggedInUser.getUser();
        List<Users> users = userRepository.findByOrganizationId(loggedInUser.getOrganizationId());
        return users.stream()
                .map(UserMapper::toUserResponse)
                .toList();
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public UserResponse editUser(UserUpdateReq updateReq) {
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
    public UserResponse me() {
        Users user = currentLoggedInUser.getUser();
        return UserMapper.toUserResponse(user);
    }

    @Override
    public UserResponse getById(Long userId) {
        Users user = userUtils.getById(userId);
        return UserMapper.toUserResponse(user);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public ResponseEntity<ApiResponse> changePassword(ChangePassword temp) {
        Users user = currentLoggedInUser.getUser();

        userUtils.validateOldPassword(user, temp.getOldPassword());

        user.setPassword(passwordEncoder.encode(temp.getNewPassword()));
        user.setUpdatedBy(user.getFullName());

        userRepository.save(user);

        return ApiResponse.success("Password Changed Successfully!");
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
        return ApiResponse.success("Password Changed Successfully!");
    }


//    @Override
//    public ApiResponse<UserDTO> createUser(UserRequest tempUser) {
//        Users userToSave = new Users();
//        try {
//            if (userRepository.findByUsername(tempUser.getEmail()) != null)
//                throw new ResourceAlreadyExistsException("There is already a user with this email.");
//
//            LocalDateTime now = LocalDateTime.now();
//            userToSave.setEmail(tempUser.getEmail());
//            userToSave.setPhoneNumber(userToSave.getPhoneNumber());
//            userToSave.setFullName(tempUser.getFullName());
//            userToSave.setRoles(Collections.singletonList(roleRepository.findByRoleName(tempUser.getRoleName())));
//            userToSave.setUsername(tempUser.getEmail());
//            userToSave.setPassword(passwordEncoder.encode(tempUser.getPassword()));
//            userToSave.setCreatedAt(now.format(dateTimeFormatter));
//            userToSave.setIsEnabled(true);
//            userToSave.setIsDeleted(false);
//            if (tempUser.getRoleName().equals("teller")) {
//                // add services with ids
//
//                List<Services> services = new ArrayList<>();
//
//                tempUser.getServices().forEach(serviceRequest -> {
//                    Optional<Services> optionalService = servicesRepository.findById(serviceRequest.getServiceId());
//                    optionalService.ifPresent(services::add);
//                });
//
//                userToSave.setServices(services);
//
//            }
//
//            Users user = userRepository.save(userToSave);
//
//            logger.info("New User is added.");
//            return new ApiResponse<>(200, "User added successfully.", new UserDTOMapper().apply(user));
//
//        } catch (ResourceAlreadyExistsException e) {
//            // Handle the case where a User with the given email already exists
//            logger.error("Error Adding User User: {}", e.getMessage());
//            return new ApiResponse<>(409, "A User with this email already exists.", null);
//        } catch (Exception e) {
//            // Handle other unexpected errors
//            logger.error("Error Adding User: {}", e.getMessage());
//            return new ApiResponse<>(500, "An unexpected error occurred.", null);
//        }
//    }
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
//    @Override
//    public ApiResponse<UserDTO> blockUser(UserRequest tempUser, Long userId) {
//        try {
//            Users user = userRepository.findByUserId(userId);
//            if (user == null) {
//                throw new ResourceNotFoundException("There is no user with this ID.");
//            }
//            user.setIsEnabled(tempUser.getIsEnabled());
//
//            userRepository.save(user);
//            return new ApiResponse<>(200, "User added successfully.", new UserDTOMapper().apply(user));
//        } catch (ResourceNotFoundException e) {
//            logger.error("Error blocking user: {}", e.getMessage());
//            return new ApiResponse<>(404, "There is no user with this ID.", null);
//        } catch (Exception e) {
//            logger.error("Error blocking user: {}", e.getMessage());
//            return new ApiResponse<>(500, "Server error please try again later!", null);
//        }
//    }

}
