package com.cams.exception;

import com.cams.dto.AccountDto;

public class AccountNotFoundException extends Exception {
    public AccountNotFoundException(String message) {
        super(message);
    }
}
