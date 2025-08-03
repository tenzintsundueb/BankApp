package com.cams.service;

import com.cams.doa.AccountDao;
import com.cams.dto.AccountDto;
import com.cams.exception.AccountNotFoundException;
import com.cams.exception.InsufficientFundsException;
import com.cams.exception.InsufficientOpeningBalanceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

@Component
public class AccountService {
    AccountDao accountDao;

    @Autowired
    public AccountService(AccountDao accountDao) {
        this.accountDao = accountDao;
    }

    public int createAccount(AccountDto accountDto) throws InsufficientOpeningBalanceException {
        // Checking opening balance
        if(accountDto.getBalance() < 3000) {
            throw new InsufficientOpeningBalanceException("Insufficient amount for opening account");
        }
        // Assigning id
        Integer id = accountDao.getId() + 1;
        accountDto.setId(id);
        return accountDao.creatAccount(accountDto);
    }

    public int deposit(AccountDto accountDto, Double amount) throws AccountNotFoundException {
        int result;
        AccountDto displayAccount = displayAccount(accountDto);
        result = accountDao.deposit(accountDto, amount);
        return result;
    }

    public int withdraw(AccountDto accountDto, Double amount) throws AccountNotFoundException, InsufficientFundsException {
        int result;
        AccountDto displayAccount = displayAccount(accountDto);
        if(displayAccount.getBalance() < amount) {
            throw new InsufficientFundsException("Insufficient Funds for withdrawal");
        }
        return accountDao.withdraw(accountDto, amount);
    }

    public AccountDto displayAccount(AccountDto accountDto) throws AccountNotFoundException {
        AccountDto displayAccount = accountDao.displayAccount(accountDto).orElseThrow(() -> new AccountNotFoundException("Invalid Account"));
        return displayAccount;
    }

    public List<AccountDto> sortAccounts(int columnIndex) {
        return accountDao.sortAccounts(columnIndex);
    }
}
