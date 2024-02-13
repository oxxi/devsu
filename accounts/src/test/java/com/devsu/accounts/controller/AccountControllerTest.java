package com.devsu.accounts.controller;

import com.devsu.accounts.dto.AccountRequest;
import com.devsu.accounts.dto.AccountResponse;
import com.devsu.accounts.service.IAccountService;
import com.devsu.accounts.util.AccountTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import java.math.BigDecimal;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;


@WebMvcTest(AccountController.class)
@ExtendWith(SpringExtension.class)
class AccountControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private IAccountService accountService;

    private AccountResponse accountResponse;

    @BeforeEach
    void setUp() {
        accountResponse = new AccountResponse();
        accountResponse.setId(1L);
        accountResponse.setAccountNumber("123456785");
        accountResponse.setType(AccountTypes.valueOf("AHORRO"));
        accountResponse.setInitialBalance(BigDecimal.valueOf(0));

    }

    @Test
    void createAccount() throws Exception {

        Mockito.when(accountService.createAccount(Mockito.any(AccountRequest.class))).thenReturn(accountResponse);

       String json = """
               {
                   "accountNumber": "123456785",
                   "type": "AHORRO",
                   "initialBalance": 0,
                   "clientId": 1
               }
               """;
        mockMvc.perform(post("/cuentas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(json)
                )
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(1L));




    }

    @Test
    void getAllAccounts() throws Exception {
        List<AccountResponse> accountResponses = List.of(accountResponse);
        Mockito.when(accountService.getAllAccounts()).thenReturn(accountResponses);
        mockMvc.perform(get("/cuentas").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }

    @Test
    void getAccount() throws Exception {
        List<AccountResponse> accountResponses = List.of(accountResponse);
        Mockito.when(accountService.getAccount(Mockito.anyLong())).thenReturn(accountResponse);
        mockMvc.perform(get("/cuentas/1").contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());

    }

    @Test
    void deleteAccount() {
    }

    @Test
    void updateAccount() {
    }
}