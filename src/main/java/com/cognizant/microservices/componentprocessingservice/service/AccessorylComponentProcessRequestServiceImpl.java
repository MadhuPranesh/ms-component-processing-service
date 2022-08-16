package com.cognizant.microservices.componentprocessingservice.service;

import com.cognizant.microservices.componentprocessingservice.client.PackagingDeliveryClient;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.LocalDate;

@Service
@Slf4j
@Transactional
public class AccessorylComponentProcessRequestServiceImpl implements ComponentProcessRequestService{

    private static final int PROCESSING_CHARGE = 300;
    @Autowired
    private EntityManager entityManager;

    @Autowired
    private PackagingDeliveryClient packagingDeliveryClient;

    @Override
    public ComponentProcessResponse processComponentDetails(String token, ComponentProcessRequest componentProcessRequest) {

        log.info("****** Started processing details for accessory type ***********");
        String componentType = componentProcessRequest.getComponentType();
        int countOfComponents = componentProcessRequest.getNoOfComponents();

        log.info("************ Saving client request detials to the database ***********");
        entityManager.persist(componentProcessRequest);
        entityManager.flush();
        log.info("************ Saved - client request detials to the database ***********");

        long requestId = componentProcessRequest.getProcessRequestId();
        int packagingAndDeliverCharge = packagingDeliveryClient.packagingAndDeliveryCost( token,componentType, countOfComponents);
        LocalDate estimatedDeliveryDate = LocalDate.now().plusDays(2);

        ComponentProcessResponse componentProcessResponse = new ComponentProcessResponse(requestId,PROCESSING_CHARGE,packagingAndDeliverCharge,estimatedDeliveryDate);
        entityManager.persist(componentProcessResponse);
        log.info("************ Response details saved to the database ***********");
        return componentProcessResponse;
    }
}
