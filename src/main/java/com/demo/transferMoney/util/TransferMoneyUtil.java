package com.demo.transferMoney.util;

import com.demo.transferMoney.exception.AccountNotFoundException;
import com.demo.transferMoney.model.Account;

import java.util.Optional;

public class TransferMoneyUtil {
    public static boolean validateAccount(Optional<Account> account){
        if(!account.isPresent()){
            throw new AccountNotFoundException(String.format("Account id %s not present",account));
        }
        if(account.get().getBalance() == null)
            throw new AccountNotFoundException(String.format("Balance id %s not present",account));

        return true;
    }
}
