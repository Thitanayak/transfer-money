package com.demo.transferMoney.builder;

import com.demo.transferMoney.domain.Request;

import java.math.BigDecimal;

public class RequestBuilder {
    private Request request;
    private RequestBuilder() {
        this.request = new Request();
    }
    public static RequestBuilder getBuilder(){
        return  new RequestBuilder();
    }
    public RequestBuilder withAccountSource(Integer accountSource){
        this.request.setAccountSource(accountSource);
        return this;
    }
    public RequestBuilder withAccountDestination(Integer accountDestination){
       this.request.setAccountDestination(accountDestination);
       return this;
    }
    public RequestBuilder withTransferAmount(BigDecimal transferAmount){
        this.request.setTransferAmount(transferAmount);
        return this;
    }
    public Request build(){
        return this.request;
    }
}
