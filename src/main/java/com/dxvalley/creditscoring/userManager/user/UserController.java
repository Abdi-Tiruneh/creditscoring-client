package com.dxvalley.creditscoring.userManager.user;

import com.dxvalley.creditscoring.userManager.user.dto.OwnerRegistrationReq;
import com.dxvalley.creditscoring.userManager.user.dto.UserRegistrationReq;
import com.dxvalley.creditscoring.userManager.user.dto.UserResponse;
import com.dxvalley.creditscoring.userManager.user.dto.UserUpdateReq;
import com.dxvalley.creditscoring.utils.ApiResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/users")
@Tag(name = "User API. GOOD TO GO")
public class UserController {

  private final UserService userService;

  @GetMapping({"/me"})
  public ResponseEntity<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> getMe() {
    return ResponseEntity.ok(userService.me());
  }

  @GetMapping({"/{id}"})
  public ResponseEntity<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> getById(@PathVariable Long id) {
    return ResponseEntity.ok(userService.getById(id));
  }

  @GetMapping("/organization")
  public ResponseEntity<List<com.dxvalley.creditscoring.userManager.user.dto.UserResponse>> getOrganizationUsers() {
    List<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> users = userService.getOrganizationUsers();
    return ResponseEntity.ok(users);
  }

  @PostMapping("/owner")
  public ResponseEntity<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> registerOwner(@RequestBody @Valid OwnerRegistrationReq ownerReq) {
    com.dxvalley.creditscoring.userManager.user.dto.UserResponse owner = userService.registerOwner(ownerReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(owner);
  }

  @PostMapping
  public ResponseEntity<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> register(@RequestBody @Valid UserRegistrationReq userReq) {
    UserResponse user = userService.register(userReq);
    return ResponseEntity.status(HttpStatus.CREATED).body(user);
  }

  @PutMapping
  public ResponseEntity<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> editUser(@RequestBody @Valid UserUpdateReq updateReq) {
    return ResponseEntity.ok(userService.editUser(updateReq));
  }

  @PutMapping("/{id}/activate-ban")
  @Operation(summary = "Ban/Activate User",
          description = "Ban/Activate a User by ID.")
  public ResponseEntity<com.dxvalley.creditscoring.userManager.user.dto.UserResponse> activateBan(@PathVariable Long id) {
    return ResponseEntity.ok(userService.activateBan(id));
  }

  @DeleteMapping({"/{id}"})
  public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
    return userService.delete(id);
  }
}



//  private boolean isOwnAccount(String userName) {
//    Authentication auth = SecurityContextHolder.getContext().getAuthentication();
//    return userRepository.findByUsername((String) auth.getPrincipal()).get().getUsername().equals(userName);
//  }
//
//  @GetMapping()
//  public ResponseEntity<?> getUsers() {
//    return new ResponseEntity<>(userService.getUsers(), HttpStatus.OK);
//  }
//
//  @GetMapping("/getTellers/{customerId}")
//  public ResponseEntity<?> getTellers(@PathVariable Long customerId) {
//    return new ResponseEntity<>(userService.getTellers(customerId),HttpStatus.OK);
//  }
//
//   @GetMapping("/getAdmins/{customerId}")
//  public ResponseEntity<?> getAdmins(@PathVariable Long customerId) {
//    return new ResponseEntity<>(userService.getAdmins(customerId),HttpStatus.OK);
//  }
//
//  @GetMapping("/{userId}")
//  public ResponseEntity<?> getByUserId(@PathVariable Long userId) {
//    var user = userRepository.findByUserId(userId);
//    if (user == null) {
//      ApiResponse<?> response = new ApiResponse<>(404, "Cannot find this user!", null);
//      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    return new ResponseEntity<>(user, HttpStatus.OK);
//  }
//
//  @GetMapping("/getUserByUsername/{username}")
//  public ResponseEntity<?> getUserByUsername(@PathVariable String username) {
//    var user = userRepository.findByUsername(username);
//
//    if (user == null) {
//      ApiResponse<?> response = new ApiResponse<>(404, "Cannot find user with this username!", null);
//      return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
//    }
//
//    return new ResponseEntity<>(user, HttpStatus.OK);
//  }
//
//
//  @PostMapping("/create")
//  public ResponseEntity<?> create(@RequestBody UserRequest user) {
//    return new ResponseEntity<>(userService.createUser(user), HttpStatus.CREATED);
//  }
//
//  @PutMapping("/changePassword/{username}")
//  public ResponseEntity<ChangePasswordResponse> changePassword(@RequestBody Users tempUser, @PathVariable String username) {
//    Users user = userRepository.findByUsername(username);
//    user.setPassword(passwordEncoder.encode(tempUser.getPassword()));
//    userRepository.save(user);
//    ChangePasswordResponse response = new ChangePasswordResponse("success");
//    return new ResponseEntity<>(response, HttpStatus.OK);
//  }
//
//  @PutMapping("/changePasswordOrUsername/{userName}/{newUsername}")
//  public Users manageAccount(@RequestBody UsernamePassword usernamePassword, @PathVariable String userName, @PathVariable Boolean newUsername) throws AccessDeniedException
//  {
//    if (isOwnAccount(userName)) {
//      Users user = userRepository.findByUsername(userName);
//      if (passwordEncoder.matches(usernamePassword.getOldPassword(), user.getPassword())) {
//        user.setPassword(passwordEncoder.encode(usernamePassword.getNewPassword()));
//      }
//      if (newUsername) {
//        user.setUsername(usernamePassword.getNewUsername());
//      }
//      Users response = userRepository.save(user);
//      response.setPassword(null);
//      return response;
//    } else
//      throw new AccessDeniedException("403 Forbidden");
//  }
//
//  @DeleteMapping("/{userId}")
//  void deleteUser(@PathVariable Long userId) {
//    userService.delete(userId);
//  }
//
//    @PutMapping("/blockUser/{userId}")
//    ApiResponse<?> blockUser(@PathVariable Long userId, @RequestBody UserRequest tempUser) {
//      return userService.blockUser(tempUser, userId);
//    }
//}

