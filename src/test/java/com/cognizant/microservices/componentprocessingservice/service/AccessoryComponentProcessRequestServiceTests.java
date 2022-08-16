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
public class AccessoryComponentProcessRequestServiceTests {

    @MockBean
    private PackagingDeliveryClient packagingDeliveryClient;

    @MockBean
    private EntityManager entityManager;

    @Autowired
    private AccessorylComponentProcessRequestServiceImpl accessorylComponentProcessRequestService;

    @Test
    void processComponentDetailsAccessory(){
        ComponentProcessRequest componentProcessRequest = new ComponentProcessRequest(1,"Madhu",1234,"Accessory","Material",5,"yes","some defect","return");
        when(packagingDeliveryClient.packagingAndDeliveryCost("sometoken","someAccessoryComponent",5)).thenReturn(1000);
        ComponentProcessResponse componentProcessResponse = accessorylComponentProcessRequestService.processComponentDetails("sometoken",componentProcessRequest);

        doNothing().when(entityManager).persist(ArgumentMatchers.any());
        assertEquals(LocalDate.now().plusDays(2),componentProcessResponse.getDateOfDelivery());
        assertEquals(300,componentProcessResponse.getProcessingCharge());
    }
}
