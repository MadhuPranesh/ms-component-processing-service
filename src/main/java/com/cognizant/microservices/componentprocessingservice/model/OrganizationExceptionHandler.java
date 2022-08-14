package com.cognizant.microservices.componentprocessingservice.model;

import feign.FeignException;
import feign.FeignException.Unauthorized;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Date;

@RestController
@ControllerAdvice
@Slf4j
public class OrganizationExceptionHandler extends ResponseEntityExceptionHandler {


    @ExceptionHandler(FeignException.Unauthorized.class)
    public final ResponseEntity<OrganizationExceptionResponse> handleFeignExceptionUnauthorized(Exception ex) {
        log.info("****** authorization failed for the user **********",ex.getMessage());
        OrganizationExceptionResponse e = new OrganizationExceptionResponse(new Date(), ex.getMessage(), "Something went wrong");
        return new ResponseEntity<>(e, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(FeignException.class)
    public final ResponseEntity<OrganizationExceptionResponse> handleFeignException(Exception ex) {
        log.info("****** Error happened with clients {}",ex.getMessage());
        OrganizationExceptionResponse e = new OrganizationExceptionResponse(new Date(), ex.getMessage(), "Something went wrong");
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<OrganizationExceptionResponse> handleAllExceptions(Exception ex) {
        log.info("****** Error happened with details {}",ex.getMessage());
        OrganizationExceptionResponse e = new OrganizationExceptionResponse(new Date(), ex.getMessage(), "Something went wrong");
        return new ResponseEntity<>(e, HttpStatus.INTERNAL_SERVER_ERROR);
    }

}
