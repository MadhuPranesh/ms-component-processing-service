package com.cognizant.microservices.componentprocessingservice.controller;

import com.cognizant.microservices.componentprocessingservice.client.AuthServiceClient;
import com.cognizant.microservices.componentprocessingservice.exception.InvalidComponentRequestDetailsException;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessRequest;
import com.cognizant.microservices.componentprocessingservice.model.ComponentProcessResponse;
import com.cognizant.microservices.componentprocessingservice.service.AccessorylComponentProcessRequestServiceImpl;
import com.cognizant.microservices.componentprocessingservice.service.ComponentTypeDeciderService;
import com.cognizant.microservices.componentprocessingservice.service.ComponentTypeDeciderServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import java.time.LocalDate;

import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
@WebMvcTest
public class ComponentProcessingControllerTests {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    AuthServiceClient authServiceClient;

    @MockBean
    AccessorylComponentProcessRequestServiceImpl componentProcessRequestService;

    @MockBean
    ComponentTypeDeciderService componentTypeDeciderService;
    private static ObjectMapper mapper = new ObjectMapper();

    @Test
    void testProcessComponentDetails() throws Exception {
        ComponentProcessResponse componentProcessResponse = new ComponentProcessResponse(1,300,1750,LocalDate.now().plusDays(5));

        when(authServiceClient.validateToken(ArgumentMatchers.any())).thenReturn(true);
        when(componentTypeDeciderService.decideComponentTypeAndProcessRequest(ArgumentMatchers.any())).thenReturn(componentProcessRequestService);
        when(componentProcessRequestService.processComponentDetails(ArgumentMatchers.any())).thenReturn(componentProcessResponse);

        String jsonObj = mapper.writeValueAsString(new ComponentProcessRequest(1,"m",123,"Integral","Material",5,"yes","some defect","return"));
        MvcResult result = mockMvc.perform(get("/process/process-detail").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","sometoken").content(jsonObj)).andExpect(status().isOk())
                .andReturn();
        assertEquals(200,result.getResponse().getStatus());
    }

    @Test
    void testCompleteProcessingRequest() throws Exception {
        when(authServiceClient.validateToken(ArgumentMatchers.any())).thenReturn(true);

        MvcResult result = mockMvc.perform(post("/process/completeProcessing/{requestID}/{creditCardNumber}/{creditLimit}/{processingCharge}",1,1234,30000,300).contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","sometoken")).andExpect(status().isOk())
                .andReturn();

        String response = result.getResponse().getContentAsString();
        assertEquals("Processed successfully!!!. Your request ID is 1",response);
    }

    @Test
    void testInvalidUsernamePathVariable() throws Exception {

        when(authServiceClient.validateToken(ArgumentMatchers.any())).thenReturn(true);
        when(componentTypeDeciderService.decideComponentTypeAndProcessRequest(ArgumentMatchers.any())).thenThrow(new InvalidComponentRequestDetailsException("Invalid user name"));

        MvcResult result = mockMvc.perform(get("/process/process-detail").contentType(MediaType.APPLICATION_JSON)
                .header("Authorization","sometoken").content(String.valueOf(new JSONObject()))).andExpect(status().isInternalServerError())
                .andReturn();
    }

}
