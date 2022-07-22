package com.demo.transferMoney.controller;

import com.demo.transferMoney.domain.Request;
import com.demo.transferMoney.domain.Response;
import com.demo.transferMoney.exception.TransferMoneyException;
import com.demo.transferMoney.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.InvalidDataAccessApiUsageException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.demo.transferMoney.exception.Error;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferController {

    @Autowired
    TransferService transferService;

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Error> handleException(Exception ex) {
        Error error = null;
        if(ex instanceof TransferMoneyException){
             error = new Error(HttpStatus.NOT_FOUND, ex.getLocalizedMessage(),ex.getLocalizedMessage());
        }
        else if(ex instanceof HttpMessageNotReadableException){
            error = new Error(HttpStatus.UNPROCESSABLE_ENTITY, "Malformed json please refer api doc",ex.getLocalizedMessage());
        }
        else if(ex instanceof InvalidDataAccessApiUsageException){
            error = new Error(HttpStatus.BAD_REQUEST, "Json element missing please refer api doc",ex.getLocalizedMessage());
        } else{
            error = new Error(HttpStatus.BAD_REQUEST, ex.getLocalizedMessage(),ex.getLocalizedMessage());
        }
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<List<Response>> transferMoney( @RequestBody Request request){
        return new ResponseEntity<>(transferService.transferMoney(request), HttpStatus.OK);
    }
}
