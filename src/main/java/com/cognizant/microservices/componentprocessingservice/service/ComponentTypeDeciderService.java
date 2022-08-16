package com.cognizant.microservices.componentprocessingservice.service;

import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import org.springframework.stereotype.Service;

public interface ComponentTypeDeciderService {
    public ComponentProcessRequestService decideComponentTypeAndProcessRequest(ComponentProcessRequest componentProcessRequest);
}
