package com.dxvalley.creditscoring.userManager.user;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByUsername(String username);
    Optional<Users> findByPhoneNumber(String phoneNumber);
    List<Users> findByOrganizationId(String organizationId);
}
