package com.cognizant.microservices.componentprocessingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="packaging-delivery-service",url = "${PACKAGEDELIVERYURL:http://localhost:8083}")
public interface PackagingDeliveryClient {
    @GetMapping("/packagingAndDeliveryService/{componentType}/{count}")
    public int packagingAndDeliveryCost(@RequestHeader(name = "Authorization") String token, @PathVariable("componentType") String componentType, @PathVariable("count") int noOfComponents);
}
