package com.cognizant.microservices.componentprocessingservice.service;

import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessResponse;

import javax.persistence.EntityManager;

public interface ComponentProcessRequestService {
    public ComponentProcessResponse processComponentDetails(ComponentProcessRequest componentProcessRequest);
}
