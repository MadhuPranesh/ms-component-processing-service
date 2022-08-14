package com.cognizant.microservices.componentprocessingservice.service;

import com.cognizant.microservices.componentprocessingservice.client.PackagingDeliveryClient;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessResponse;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import javax.persistence.EntityManager;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@SpringBootTest
public class IntegralComponentProcessRequestServiceTests {

    @MockBean
    private PackagingDeliveryClient packagingDeliveryClient;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private IntegralComponentProcessRequestServiceImpl integralComponentProcessRequestService;

    @Test
    void processComponentDetailIntegral(){
        ComponentProcessRequest componentProcessRequest = new ComponentProcessRequest(1,"Madhu",1234,"Integral","Material",5);
        when(packagingDeliveryClient.packagingAndDeliveryCost("someAccessoryComponent",5)).thenReturn(1000);
        ComponentProcessResponse componentProcessResponse = integralComponentProcessRequestService.processComponentDetails(componentProcessRequest);

        doNothing().when(entityManager).persist(ArgumentMatchers.any());
        assertEquals(LocalDate.now().plusDays(5),componentProcessResponse.getDateOfDelivery());
        assertEquals(500,componentProcessResponse.getProcessingCharge());
    }
}
