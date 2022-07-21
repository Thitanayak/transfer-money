package com.demo.transferMoney.service;

import com.demo.transferMoney.Repository.AccountRepository;
import com.demo.transferMoney.builder.ResponseBuilder;
import com.demo.transferMoney.domain.Request;
import com.demo.transferMoney.domain.Response;
import com.demo.transferMoney.exception.AccountNotFoundException;
import com.demo.transferMoney.model.Account;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class TransferService {

    @Autowired
    AccountRepository accountRepository;

   public List<Response> transferMoney(Request request){
       BigDecimal transferAmount = request.getTransferAmount();
       Optional<Account> accountSource = accountRepository.findById(request.getAccountSource());
       Optional<Account> accountDestination = accountRepository.findById(request.getAccountDestination());
       List<Response> responseList = null;
       if(accountSource.isPresent() && accountDestination.isPresent() && !(accountSource.get().getBalance().compareTo(BigDecimal.ZERO) == 0)){
           BigDecimal accountSourceBalance = accountSource.get().getBalance();
           BigDecimal accountDestinationBalance = accountDestination.get().getBalance();

           accountSource.get().setBalance(accountSourceBalance.subtract(transferAmount));
           accountDestination.get().setBalance(accountDestinationBalance.add(transferAmount));
           accountRepository.save(accountSource.get());
           accountRepository.save(accountDestination.get());

           Response responseSource = ResponseBuilder.newBuilder()
                   .withAccountNumber(request.getAccountSource())
                   .withPreviousBalance(accountSourceBalance)
                   .withCurrentBalance(accountSource.get().getBalance())
                   .build();

           Response responseDestination = ResponseBuilder.newBuilder()
                   .withAccountNumber(request.getAccountDestination())
                   .withPreviousBalance(accountDestinationBalance)
                   .withCurrentBalance(accountDestination.get().getBalance())
                   .build();

           responseList = new ArrayList<>();
           responseList.add(responseSource);
           responseList.add(responseDestination);

       } else{
           if(!accountSource.isPresent())
           throw new AccountNotFoundException(String.format
                   ("Account Doesn't exist %s",String.valueOf(request.getAccountSource())));
           else if(!accountDestination.isPresent()){
               throw new AccountNotFoundException(String.format
                       ("Account Doesn't exist %s",String.valueOf(request.getAccountDestination())));
           }else if(accountSource.get().getBalance().compareTo(BigDecimal.ZERO) == 0){
               throw new AccountNotFoundException(String.format
                       ("Account balance is zero in %s",String.valueOf(request.getAccountSource())));
           }
       }
       return responseList;
   }
}
