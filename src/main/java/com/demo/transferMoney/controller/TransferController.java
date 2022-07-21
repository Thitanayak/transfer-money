package com.demo.transferMoney.controller;

import com.demo.transferMoney.domain.Request;
import com.demo.transferMoney.domain.Response;
import com.demo.transferMoney.exception.AccountNotFoundException;
import com.demo.transferMoney.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.demo.transferMoney.exception.Error;

import java.util.List;

@RestController
@RequestMapping("/api")
public class TransferController {


    @Autowired
    TransferService transferService;

    @ExceptionHandler(AccountNotFoundException.class)
    public ResponseEntity<Error> handleException(AccountNotFoundException e) {
        Error error = new Error(HttpStatus.NOT_FOUND, e.getLocalizedMessage(),null);
        return new ResponseEntity<>(error, error.getHttpStatus());
    }

    @PostMapping("/transferMoney")
    public ResponseEntity<List<Response>> transferMoney(@RequestBody Request request){
        return new ResponseEntity<>(transferService.transferMoney(request), HttpStatus.OK);
    }
}
