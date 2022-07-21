package com.demo.transferMoney.domain;

import lombok.Data;

import java.math.BigDecimal;

@Data
public class Response {
    private Integer accountNumber;
    private BigDecimal previousBalance;
    private BigDecimal currentBalance;
}
