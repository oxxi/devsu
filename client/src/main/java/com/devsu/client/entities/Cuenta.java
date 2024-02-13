package com.devsu.client.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

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
}
