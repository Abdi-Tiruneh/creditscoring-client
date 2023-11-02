package com.dxvalley.creditscoring.businessRule;

import com.dxvalley.creditscoring.businessRule.dto.BusinessRuleReq;
import com.dxvalley.creditscoring.exceptions.customExceptions.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BusinessRuleServiceImpl implements BusinessRuleService {
    private final BusinessRuleRepository businessRuleRepository;

    @Override
    public List<BusinessRule> addBusinessRule(List<BusinessRuleReq> businessRuleReqList) {

        List<BusinessRule> businessRules = createBusinessRoles(businessRuleReqList);

        // delete all existing rules and create new one by serviceId
        businessRuleRepository.deleteByServiceId(businessRuleReqList.get(0).getServiceId());

        return businessRuleRepository.saveAll(businessRules);
    }

    //TODO: force min and max score validations. Make them not repeat a given service.

    List<BusinessRule> createBusinessRoles(List<BusinessRuleReq> businessRuleReqList) {
        

        List<BusinessRule> businessRules = new ArrayList<>();

        for (BusinessRuleReq businessRuleReq : businessRuleReqList) {
            BusinessRule businessRule = new BusinessRule();

            businessRule.setMinScore(businessRuleReq.getMinScore());
            businessRule.setMaxScore(businessRuleReq.getMaxScore());
            businessRule.setAmountAllowed(businessRuleReq.getAmountAllowed());
            businessRule.setRemark(businessRuleReq.getRemark());
            businessRule.setXCode(businessRuleReq.getXCode());
            businessRule.setServiceId(businessRuleReq.getServiceId());

            businessRules.add(businessRule);
        }

        return businessRules;
    }

    @Override
    public List<BusinessRule> updateBusinessRule(List<BusinessRule> businessRules) {
//        delete all existing rules and create new one
        return businessRuleRepository.saveAll(businessRules);
    }

    @Override
    public List<BusinessRule> getServiceBusinessRules(Long serviceId) {
        List<BusinessRule> businessRules = businessRuleRepository.findByServiceId(serviceId);

        if (businessRules.isEmpty())
            throw new ResourceNotFoundException("No Business Rule is defined for this service");

        return businessRules;
    }


//    @Override
//    public void addBusinessRule(BusinessRuleAddRequest tempBusinessRule, int serviceId) {
//            BusinessRule businessRule = new BusinessRule();
//
//            businessRule.setMinScore(tempBusinessRule.getMinScore());
//            businessRule.setMaxScore(tempBusinessRule.getMaxScore());
//            businessRule.setAmountAllowed(tempBusinessRule.getAmountAllowed());
//            businessRule.setRemark(tempBusinessRule.getRemark());
//            businessRule.setXCode(tempBusinessRule.getXCode());
//
//            //saving caused the error while getting services and business rules
//            Optional<Services> servicesOptional = servicesRepository.findById(serviceId);
//            if (servicesOptional.isPresent()) {
//                Services services = servicesOptional.get();
//                businessRule.setServices(services);
//                services.getBusinessRules().add(businessRule);
//                servicesRepository.save(services);
//            }


//    @Override
//    public void delete(Long BusinessRuleId) {
//        try {
//            logger.info("businessRule Deleted");
//            businessRuleRepository.deleteById(BusinessRuleId);
//        } catch (DataAccessException ex) {
//            logger.error("Error Deleting BusinessRule: {}", ex.getMessage());
//            throw new RuntimeException("Error Deleting BusinessRule ", ex);
//        }
//    }
//
//    @Override
//    public void update(BusinessRule BusinessRule) {
//        try {
//            businessRuleRepository.save(BusinessRule);
//            logger.info("BusinessRule updated");
//        } catch (DataAccessException ex) {
//            logger.error("Error: ", ex.getMessage());
//            throw new RuntimeException("Error: ", ex);
//        }
//    }

}

