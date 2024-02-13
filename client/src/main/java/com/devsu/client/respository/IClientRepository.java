package com.devsu.client.respository;

import com.devsu.client.entities.Cliente;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IClientRepository extends ListCrudRepository<Cliente, Long> {

    Cliente findByNombre(String nombre);
}
