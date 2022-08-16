package com.cognizant.microservices.componentprocessingservice.service;

import com.cognizant.microservices.componentprocessingservice.exception.InvalidComponentRequestDetailsException;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Slf4j
public class ComponentTypeDeciderServiceImplTests {

    @Autowired
    private ComponentTypeDeciderService componentTypeDeciderService;
    @MockBean
    private IntegralComponentProcessRequestServiceImpl integralComponentProcessRequestService;

    @MockBean
    private AccessorylComponentProcessRequestServiceImpl accessorylComponentProcessRequestService;

    @Test
    void testComponentTypeIntegral(){
        ComponentProcessRequest componentProcessRequest = new ComponentProcessRequest(1,"user",1234,"integral","material",5,"yes","some defect","return");
        ComponentProcessRequestService componentProcessRequestService = componentTypeDeciderService.decideComponentTypeAndProcessRequest(componentProcessRequest);
        assertTrue(componentProcessRequestService instanceof IntegralComponentProcessRequestServiceImpl);
    }

    @Test
    void testComponentTypeAccessory(){
        ComponentProcessRequest componentProcessRequest = new ComponentProcessRequest(1,"user",1234,"accessory","material",5,"yes","some defect","return");
        ComponentProcessRequestService componentProcessRequestService = componentTypeDeciderService.decideComponentTypeAndProcessRequest(componentProcessRequest);
        assertTrue(componentProcessRequestService instanceof AccessorylComponentProcessRequestServiceImpl);
    }
    @Test
    void testInvalidUsername(){
        ComponentProcessRequest componentProcessRequest = new ComponentProcessRequest(1,null,1234,"accessory","material",5,"yes","some defect","return");
        InvalidComponentRequestDetailsException thrownException = assertThrows(InvalidComponentRequestDetailsException.class,
                () -> componentTypeDeciderService.decideComponentTypeAndProcessRequest(componentProcessRequest));
    }
}
