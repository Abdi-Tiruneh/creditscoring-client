package com.dxvalley.creditscoring.businessRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface BusinessRuleRepository extends JpaRepository<BusinessRule, Long> {
    List<BusinessRule> findByServiceId(Long serviceId);
}
