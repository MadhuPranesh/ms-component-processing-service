package com.cognizant.microservices.componentprocessingservice.model;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Date;


@AllArgsConstructor
@Data
public class OrganizationExceptionResponse {

    private Date date;
    private String message;
    private String details;
}

