package com.dxvalley.creditscoring.customerservice.tellerService;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TellerServiceMappingRepository extends JpaRepository<TellerServiceMapping, Long> {

    Optional<TellerServiceMapping> findByUserId(Long userId);
}
