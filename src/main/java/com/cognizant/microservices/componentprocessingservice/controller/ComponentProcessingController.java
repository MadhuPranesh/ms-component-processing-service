package com.cognizant.microservices.componentprocessingservice.controller;

import com.cognizant.microservices.componentprocessingservice.client.AuthServiceClient;
import com.cognizant.microservices.componentprocessingservice.exception.InvalidComponentRequestDetailsException;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessResponse;
import com.cognizant.microservices.componentprocessingservice.service.ComponentProcessRequestService;
import com.cognizant.microservices.componentprocessingservice.service.ComponentTypeDeciderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/process")
@Slf4j
@CrossOrigin
public class ComponentProcessingController {

    @Autowired
    private ComponentTypeDeciderService componentTypeDeciderService;

    @Autowired
    private AuthServiceClient authClient;


    @PostMapping("/process-detail")
    public ResponseEntity<ComponentProcessResponse> processComponentDetails (@RequestHeader("Authorization") String token, @RequestBody ComponentProcessRequest componentProcessRequest) throws InvalidComponentRequestDetailsException{
        log.info("******** Starting auth check for cmp service **********");
        authClient.validateToken(token);
        log.info("******** Auth check successfull for cmp service***********");

        ComponentProcessRequestService componentProcessRequestService = componentTypeDeciderService.decideComponentTypeAndProcessRequest(componentProcessRequest);
        log.info("******* Received component type is {}",componentProcessRequest.getComponentType());

        ComponentProcessResponse componentProcessResponse = componentProcessRequestService.processComponentDetails(token,componentProcessRequest);

        return new ResponseEntity<>(componentProcessResponse, HttpStatus.OK);
    }

    @PostMapping("/completeProcessing/{requestID}/{creditCardNumber}/{creditLimit}/{processingCharge}")
    public ResponseEntity<String> completeProcessingRequest(
            @RequestHeader("Authorization") String token,
            @PathVariable("requestID") int requestID, @PathVariable("creditCardNumber") long creditCardNumber,
            @PathVariable("creditLimit") double creditLimit,
            @PathVariable("processingCharge") double processingCharge
    ){
        log.info("******** Starting auth check for cmp service **********");
        authClient.validateToken(token);
        log.info("******** Auth check successfull for cmp service***********");

        log.info("********** Payment Processing started *********");
        String response = "Processed successfully!!!. Your request ID is " + requestID;
        log.info("********** Payment Processing over *********");

        return new ResponseEntity<>(response,HttpStatus.OK);
    }
}
