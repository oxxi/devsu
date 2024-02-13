package com.devsu.accounts.controller;


import com.devsu.accounts.dto.AccountRequest;
import com.devsu.accounts.dto.AccountResponse;
import com.devsu.accounts.service.IAccountService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cuentas")
public class AccountController {

    private final IAccountService accountService;

    public AccountController(IAccountService accountService) {
        this.accountService = accountService;
    }


    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public AccountResponse createAccount(@RequestBody @Valid AccountRequest dto){
        return accountService.createAccount(dto);
    }

    @GetMapping
    public List<AccountResponse> getAllAccounts(){
        return accountService.getAllAccounts();
    }

    @GetMapping("/{id}")
    public AccountResponse getAccount(@PathVariable("id") Long id){
        return accountService.getAccount(id);
    }

    @DeleteMapping("/{id}")
    public void deleteAccount(@PathVariable Long id){
        accountService.deleteAccount(id);
    }

    @PutMapping("/{id}")
    public void updateAccount(@PathVariable Long id, @RequestBody AccountRequest dto){
        accountService.updateAccount(id,dto);
    }
}
