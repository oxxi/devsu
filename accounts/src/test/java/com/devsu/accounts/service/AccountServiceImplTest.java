package com.devsu.accounts.service;

import com.devsu.accounts.dto.AccountRequest;
import com.devsu.accounts.dto.AccountResponse;
import com.devsu.accounts.entities.Cliente;
import com.devsu.accounts.entities.Cuenta;
import com.devsu.accounts.repository.IAccountRepository;
import com.devsu.accounts.repository.IClientRepository;
import com.devsu.accounts.util.AccountTypes;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class AccountServiceImplTest {



    @Mock
    private IAccountRepository repository;

    @Mock
    private IClientRepository clientRepository;

    @InjectMocks
    private AccountServiceImpl service;

    private AccountRequest accountRequest;

    private Cliente cliente;

    private Cuenta cuenta;

    @BeforeEach
    void setUp() {
        accountRequest = new AccountRequest();
        accountRequest.setInitialBalance(BigDecimal.valueOf(0));
        accountRequest.setClientId(1L);
        accountRequest.setAccountNumber("123456");
        accountRequest.setType(AccountTypes.AHORRO);

        cliente = new Cliente();
        cliente.setId(1L);
        cliente.setNombre("Juan");
        cliente.setPassword("1233333");
        cliente.setStatus(true);


        cuenta = new Cuenta();
        cuenta.setNumeroCuenta("123456");
        cuenta.setTipoCuenta(String.valueOf(AccountTypes.AHORRO));
        cuenta.setId(1L);
        cuenta.setEstado(true);
        cuenta.setCliente(cliente);
    }

    @Test
    void createAccount() {
        Mockito.when(repository.findByNumeroCuenta(Mockito.anyString())).thenReturn(null);
        Mockito.when(clientRepository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(cliente));
        Mockito.when(repository.save(Mockito.any())).thenReturn(cuenta);
        AccountResponse account = service.createAccount(accountRequest);

        assertNotNull(account);

        assertEquals(accountRequest.getAccountNumber(), account.getAccountNumber());


    }

    @Test
    void getAllAccounts() {
        Mockito.when(repository.findAll()).thenReturn(List.of(cuenta));
        List<AccountResponse> allAccounts = service.getAllAccounts();
        assertNotNull(allAccounts);

        assertEquals(1, allAccounts.size());

    }

    @Test
    void getAccount() {
        Mockito.when(repository.findById(Mockito.anyLong())).thenReturn(java.util.Optional.of(cuenta));
        AccountResponse account = service.getAccount(1L);
        assertNotNull(account);

        assertEquals(1L, account.getId());
    }

    @Test
    void deleteAccount() {
    }

    @Test
    void updateAccount() {
    }
}