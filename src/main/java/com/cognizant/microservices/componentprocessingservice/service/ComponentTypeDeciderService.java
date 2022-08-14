package com.cognizant.microservices.componentprocessingservice.service;

import com.cognizant.microservices.componentprocessingservice.exception.InvalidComponentRequestDetailsException;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;

@Service
@Transactional
@Slf4j
public class ComponentTypeDeciderService {

    @Autowired
    private AccessorylComponentProcessRequestServiceImpl accessorylComponentProcessRequestService;
    @Autowired
    private IntegralComponentProcessRequestServiceImpl integralComponentProcessRequestService;

    public ComponentProcessRequestService decideComponentTypeAndProcessRequest(ComponentProcessRequest componentProcessRequest){

        if(componentProcessRequest.getUserName() == null ){
            log.info("********* username is invalid with being {}",componentProcessRequest.getUserName());
            throw new InvalidComponentRequestDetailsException("Invalid request detials :"+componentProcessRequest.toString());
        }

        if(componentProcessRequest.getComponentType().equalsIgnoreCase("integral")) {
            log.info("*********** the component type is Integral **********");
            return integralComponentProcessRequestService;
        }
        else {
            log.info("*********** the component type is Accessory **********");
            return accessorylComponentProcessRequestService;
        }
    }
}
