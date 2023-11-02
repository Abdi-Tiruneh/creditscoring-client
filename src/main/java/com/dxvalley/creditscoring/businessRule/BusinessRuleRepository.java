package com.dxvalley.creditscoring.businessRule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface BusinessRuleRepository extends JpaRepository<BusinessRule, Long> {
    List<BusinessRule> findByServiceId(Long serviceId);
    
    @Transactional
    void deleteByServiceId(Long serviceId);
}
