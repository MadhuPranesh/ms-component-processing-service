package com.cognizant.microservices.componentprocessingservice.exception;

public class InvalidComponentRequestDetailsException extends RuntimeException {
    public InvalidComponentRequestDetailsException(String msg)
    {
        super(msg);
    }
}
