package com.devsu.accounts.service;

import com.devsu.accounts.dto.AccountRequest;
import com.devsu.accounts.dto.AccountResponse;

import java.util.List;

public interface IAccountService {

    AccountResponse createAccount(AccountRequest dto);

    List<AccountResponse> getAllAccounts();

    AccountResponse getAccount(Long id);

    void deleteAccount(Long id);


    void updateAccount(Long id, AccountRequest dto);
}
