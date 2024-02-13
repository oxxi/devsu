package com.devsu.accounts.service;

import com.devsu.accounts.dto.AccountRequest;
import com.devsu.accounts.dto.AccountResponse;
import com.devsu.accounts.entities.Cliente;
import com.devsu.accounts.entities.Cuenta;
import com.devsu.accounts.repository.IAccountRepository;
import com.devsu.accounts.repository.IClientRepository;
import com.devsu.accounts.util.AccountTypes;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;


@Service
public class AccountServiceImpl implements IAccountService{

    private final IAccountRepository repository;
    private final IClientRepository  clientRepository;

    public AccountServiceImpl(IAccountRepository repository, IClientRepository clientRepository) {
        this.repository = repository;
        this.clientRepository = clientRepository;
    }


    @Override
    public AccountResponse createAccount(AccountRequest dto) {
        Cuenta account = repository.findByNumeroCuenta(dto.getAccountNumber());
        if(account != null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("La cuenta %s ya existe", dto.getAccountNumber()));
        }

        //check if client exists
        Cliente client = clientRepository.findById(dto.getClientId()).orElseThrow(()->
                        new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("El cliente %s no existe", dto.getClientId()))
                );

        Cuenta entity = new Cuenta();
        entity.setNumeroCuenta(dto.getAccountNumber());
        entity.setEstado(true);
        entity.setTipoCuenta(dto.getType().toString());
        entity.setSaldoInicial(dto.getInitialBalance());
        entity.setCliente(client);

        entity = repository.save(entity);

        return entity.toDto();

    }

    @Override
    public List<AccountResponse> getAllAccounts() {
       List<Cuenta> accounts = repository.findAll();
       return  accounts.stream().map(x->{
                        AccountResponse  response = new AccountResponse();
                        response.setId(x.getId());
                        response.setAccountNumber(x.getNumeroCuenta());
                        response.setType(AccountTypes.valueOf(x.getTipoCuenta()));
                        response.setInitialBalance(x.getSaldoInicial());
                        response.setClientId(x.getCliente().getId());
                        response.setStatus(x.getEstado());
                        return response;
                 }).toList();
    }

    @Override
    public AccountResponse getAccount(Long id) {

        Cuenta cuenta = repository.findById(id)
                                .orElseThrow(
                                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                String.format("La cuenta %s no existe", id))
                                );


        AccountResponse response = new AccountResponse();
        response.setId(cuenta.getId());
        response.setAccountNumber(cuenta.getNumeroCuenta());
        response.setType(AccountTypes.valueOf(cuenta.getTipoCuenta()));
        response.setInitialBalance(cuenta.getSaldoInicial());
        response.setClientId(cuenta.getCliente().getId());
        response.setStatus(cuenta.getEstado());
        return response;
    }

    @Override
    public void deleteAccount(Long id) {
        Cuenta cuenta = repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("La cuenta %s no existe", id))
                );
        repository.delete(cuenta);
    }

    @Override
    public void updateAccount(Long id, AccountRequest dto) {
        Cuenta cuenta = repository.findById(id)
                .orElseThrow(
                        () -> new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("La cuenta %s no existe", id))
                );
        //TODO: el requerimiento no dice nada acerca de condiciones para la actualización de la cuenta
        //asi que se asume que se puede actualizar la cuenta sin ninguna condición.

        //check if client exists
        Cliente client = clientRepository.findById(dto.getClientId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("El cliente %s no existe", dto.getClientId())));

        cuenta.setSaldoInicial(dto.getInitialBalance());
        cuenta.setTipoCuenta(dto.getType().toString());
        cuenta.setCliente(client);

        repository.save(cuenta);
    }
}
