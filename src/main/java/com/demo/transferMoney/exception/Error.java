package com.demo.transferMoney.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;

@Data
public class Error {
    private  HttpStatus httpStatus;
    private  String message;
    private  String debugMessage;

    public Error(HttpStatus httpStatus, String message, String debugMessage) {
        this.httpStatus = httpStatus;
        this.message = message;
        this.debugMessage = debugMessage;
    }
}
