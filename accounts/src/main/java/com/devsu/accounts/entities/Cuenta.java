package com.devsu.accounts.entities;

import com.devsu.accounts.dto.AccountResponse;
import com.devsu.accounts.util.AccountTypes;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Entity
@Table(name = "cuentas")
@Getter
@Setter
public class Cuenta {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "numero_cuenta")
    private String numeroCuenta;

    @Column(name = "tipo_cuenta")
    private String tipoCuenta;

    @Column(name = "saldo_inicial")
    private BigDecimal saldoInicial;

    @Column(name = "estado")
    private Boolean estado;

    @ManyToOne
    @JoinColumn(name = "cliente_id", referencedColumnName = "id")
    private Cliente cliente;

    @OneToMany(mappedBy = "cuenta")
    private List<Movimiento> movimientos;


    public AccountResponse toDto(){
        AccountResponse response = new AccountResponse();
        response.setId(id);
        response.setAccountNumber(numeroCuenta);
        response.setType(AccountTypes.valueOf(tipoCuenta));
        response.setInitialBalance(saldoInicial);
        response.setClientId(cliente.getId());
        response.setStatus(estado);
        return response;
    }
}
