package com.devsu.accounts.service;

import com.devsu.accounts.dto.MovementRequest;
import com.devsu.accounts.dto.MovementResponse;
import com.devsu.accounts.entities.Cuenta;
import com.devsu.accounts.entities.Movimiento;
import com.devsu.accounts.repository.IAccountRepository;
import com.devsu.accounts.repository.IMovementRepository;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import java.math.BigDecimal;
import java.util.List;

@Service
public class MovementServiceImpl implements IMovementService{

    private final IMovementRepository movementRepository;
    private final IAccountRepository  accountRepository;

    public MovementServiceImpl(IMovementRepository movementRepository, IAccountRepository accountRepository) {
        this.movementRepository = movementRepository;
        this.accountRepository = accountRepository;
    }

    //F2: Registro de movimientos al registrar la cuenta debe de tener en cuenta lo siguiente
    // -Para movimiento se puede tener valores positivos o negativos
    // -Al realizar movimientos se debe de actualizar el saldo disponible
    // -Se debe llevar el registro de la transaccion realizada
    //F3: Registro de movimientos: al realizar un movimiento el cual no cuente con saldo debe alertar mediante el mensaje "Saldo no disponible"
    // - defina segun experiencia la mejor manera de capturar y mostra el error
    @Override
    public MovementResponse createMovement(MovementRequest request) {

        return validateAndRegisterMovement(request);
    }

    @Override
    public MovementResponse getMovement(Long id) {

        Movimiento movimiento = movementRepository.findById(id)
                                .orElseThrow(() ->
                                                new ResponseStatusException(HttpStatus.NOT_FOUND,
                                                        String.format("Movimiento %s no existe", id))
                                );
        return movimiento.toResponse();

    }

    @Override
    public MovementResponse updateMovement(Long id, MovementRequest request) {
        Movimiento movimiento = movementRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Movimiento %s no existe", id))
                );
        //TODO: actualizar un movimiento es riesgoso si no se tiene las reglas de negocio
        // por lo momentos se asume que es un update normal asi que solo se reemplaza el valor del movimiento
        // en un scenario real deberia de haber mas reglas de negocio y calulos para actualizar el saldo

        movimiento.setValor(request.getAmount());
        movimiento = movementRepository.save(movimiento);
        return movimiento.toResponse();
    }

    @Override
    public void deleteMovement(Long id) {
        Movimiento movimiento = movementRepository.findById(id)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND,
                                String.format("Movimiento %s no existe", id))
                );
        movementRepository.delete(movimiento);
    }

    @Override
    public List<MovementResponse> getMovements() {
        Sort sort = Sort.by(Sort.Order.asc("cuenta.id"), Sort.Order.desc("fecha"));
      return  movementRepository.findAll(sort).stream().map(Movimiento::toResponse).toList();
    }


    private MovementResponse validateAndRegisterMovement(MovementRequest request) {
        Cuenta cuenta = accountRepository.findByNumeroCuenta(request.getAccountNumber());
        if(cuenta == null){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, String.format("La cuenta %s no existe", request.getAccountNumber()));
        }

        BigDecimal currentBalance = calculateCurrentBalance(cuenta);
        BigDecimal newBalance = currentBalance.add(request.getAmount());

        // Verificar si el movimiento lleva el saldo a negativo
        if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Saldo no disponible");
        }

        // Registrar movimiento
        return registerMovement(cuenta, request.getAmount(), newBalance);
    }

    private BigDecimal calculateCurrentBalance(Cuenta cuenta) {
        // Retorna el saldo actual de la cuenta.
        return cuenta.getMovimientos().isEmpty() ? cuenta.getSaldoInicial() :
                cuenta.getMovimientos().get(cuenta.getMovimientos().size() - 1).getSaldo();
    }

    private MovementResponse registerMovement(Cuenta cuenta, BigDecimal amount, BigDecimal nuevoSaldo) {
        Movimiento movimiento = new Movimiento();
        movimiento.setValor(amount);
        movimiento.setSaldo(nuevoSaldo);
        movimiento.setCuenta(cuenta);
        movimiento = movementRepository.save(movimiento);
        return movimiento.toResponse();
    }


}