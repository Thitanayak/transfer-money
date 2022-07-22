package com.demo.transferMoney.domain;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.sun.istack.NotNull;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

@Data
public class Request {

    private Integer accountSource;

    private Integer accountDestination;

    private BigDecimal transferAmount;


}
