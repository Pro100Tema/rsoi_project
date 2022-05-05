package com.example.gateway;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@RestControllerAdvice
public class ServiceUnavailableExceptionHandler {

    @ExceptionHandler(FeignMessageException.class)
    @ResponseStatus(value = HttpStatus.SERVICE_UNAVAILABLE)
    public ErrorMessage feignExceptionHandler(Exception ex, WebRequest request) {
        return new ErrorMessage(HttpStatus.SERVICE_UNAVAILABLE.value(), new Date(), ex.getMessage(),
                request.getDescription(false));
    }
}
