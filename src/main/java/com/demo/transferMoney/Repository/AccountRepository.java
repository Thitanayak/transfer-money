package com.demo.transferMoney.Repository;

import com.demo.transferMoney.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AccountRepository extends JpaRepository<Account,Integer>{
}
