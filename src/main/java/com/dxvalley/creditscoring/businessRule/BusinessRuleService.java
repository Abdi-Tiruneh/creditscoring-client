package com.dxvalley.creditscoring.businessRule;

import com.dxvalley.creditscoring.businessRule.dto.BusinessRuleReq;

import java.util.List;

public interface BusinessRuleService {
    List<BusinessRule> addBusinessRule(List<BusinessRuleReq> businessRuleList);
    List<BusinessRule> updateBusinessRule(List<BusinessRule> businessRules);
    List<BusinessRule> getServiceBusinessRules(Long serviceId);
}
