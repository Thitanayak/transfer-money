package com.demo.transferMoney.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import java.math.BigDecimal;

@Entity
@Table(name = "account")
public class Account {
    @Id
    @Column(nullable = false)
    private Integer accountNumber;

    @Column
    private BigDecimal balance;

    @Column
    private Integer transactionId;

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public Account(){

    }

    public Account(Integer accountNumber, BigDecimal balance, Integer transactionId) {
        this.accountNumber = accountNumber;
        this.balance = balance;
        this.transactionId = transactionId;
    }

    public Integer getAccountNumber() {

        return accountNumber;
    }

    public void setAccountNumber(Integer accountNumber) {

        this.accountNumber = accountNumber;
    }

}