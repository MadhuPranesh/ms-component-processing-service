package com.cognizant.microservices.componentprocessingservice.model;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.time.LocalDate;

@Slf4j
@Data
@Table(name="component_service_processing_details")
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ComponentProcessResponse {

    @Id
    private long processRequestId;
    private double processingCharge;
    private double packagingAndDeliveryCharge;
    private LocalDate dateOfDelivery;
}
