package com.devsu.accounts.repository;

import com.devsu.accounts.entities.Cliente;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends ListCrudRepository<Cliente, Long> {
    Cliente findByNombre(String nombre);

}
