package com.cognizant.microservices.componentprocessingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.*;

@Slf4j
@Data
@Entity
@Table(name="component_service_request_details")
@AllArgsConstructor
@NoArgsConstructor
public class ComponentProcessRequest {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private long processRequestId;
    private String userName;
    private long contactNumber;
    private String componentType;
    private String componentName;
    private int noOfComponents;
    private String isPriorityOne;
    private String defectiveDetails;
    private String returnOrReplacement;

}
