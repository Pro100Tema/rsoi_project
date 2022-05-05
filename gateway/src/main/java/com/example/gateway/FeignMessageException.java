package com.example.gateway;

import org.springframework.http.HttpStatus;

public class FeignMessageException extends RuntimeException {

    private static final long serialVersionUID = 1L;
    private int code;
    private String msg;

    public FeignMessageException(String msg) {
        super(msg);
    }
}
