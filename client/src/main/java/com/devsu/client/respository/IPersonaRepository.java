package com.devsu.client.respository;

import com.devsu.client.entities.Persona;
import org.springframework.data.repository.ListCrudRepository;

public interface IPersonaRepository extends ListCrudRepository<Persona, Long> {
}
