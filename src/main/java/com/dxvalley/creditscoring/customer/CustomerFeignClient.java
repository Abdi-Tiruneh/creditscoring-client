package com.dxvalley.creditscoring.customer;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(name = "customer", url = "http://10.1.177.121:8888")
public interface CustomerFeignClient {

    @RequestMapping(method = RequestMethod.PUT, value = "/api/v1/customers/activate/{code}", consumes = MediaType.APPLICATION_JSON_VALUE)
    String activateCustomer(@RequestParam("code") String code);
}