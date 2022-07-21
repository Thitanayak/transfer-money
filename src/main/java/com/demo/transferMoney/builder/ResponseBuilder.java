package com.demo.transferMoney.builder;

import com.demo.transferMoney.domain.Response;

import java.math.BigDecimal;

public class ResponseBuilder {
    private Response response;
    public static ResponseBuilder newBuilder(){
        return new ResponseBuilder();
    }

    private ResponseBuilder() {
        this.response = new Response();
    }

    public ResponseBuilder withPreviousBalance(BigDecimal balance){
        this.response.setPreviousBalance(balance);
        return this;
    }

    public ResponseBuilder withCurrentBalance(BigDecimal balance){
        this.response.setCurrentBalance(balance);
        return this;
    }

    public ResponseBuilder withAccountNumber(Integer accountNumber){
        this.response.setAccountNumber(accountNumber);
        return this;
    }

    public Response build(){
        return this.response;
    }
}
