package com.demo.transferMoney.exception;

public class AccountNotFoundException extends RuntimeException{
    public AccountNotFoundException(String s){
     super(s);
    }
}
