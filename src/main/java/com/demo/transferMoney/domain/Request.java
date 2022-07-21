package com.demo.transferMoney.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Request {
    private Integer accountSource;
    private Integer accountDestination;
    private BigDecimal transferAmount;


}
