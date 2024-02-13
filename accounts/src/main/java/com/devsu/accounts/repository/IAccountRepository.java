package com.devsu.accounts.repository;

import com.devsu.accounts.entities.Cuenta;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IAccountRepository extends ListCrudRepository<Cuenta, Long> {

    Cuenta findByNumeroCuenta(String numeroCuenta);

}
