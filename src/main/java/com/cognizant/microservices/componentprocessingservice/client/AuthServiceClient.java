package com.cognizant.microservices.componentprocessingservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

@FeignClient(name="auth-service",url = "${AUTHSERVICEURL:http://localhost:8081}")
public interface AuthServiceClient {

    @GetMapping("/auth/validateToken")
    public boolean validateToken(@RequestHeader("Authorization") String token);
}
