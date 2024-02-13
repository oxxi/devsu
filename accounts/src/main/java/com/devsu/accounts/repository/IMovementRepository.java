package com.devsu.accounts.repository;

import com.devsu.accounts.entities.Movimiento;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.ListCrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Repository
public interface IMovementRepository extends JpaRepository<Movimiento, Long> {


    @Query("SELECT m FROM Movimiento m WHERE m.cuenta.cliente.id = :clienteId AND m.fecha >= :fechaInicio AND m.fecha <= :fechaFin ORDER BY m.cuenta.id ASC, m.fecha DESC")
    List<Movimiento> findMovementsByClienteAndDateRange(@Param("clienteId") Long clienteId,
                                                        @Param("fechaInicio") LocalDateTime fechaInicio,
                                                        @Param("fechaFin") LocalDateTime fechaFin);

}
