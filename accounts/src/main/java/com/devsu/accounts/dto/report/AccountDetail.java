package com.devsu.accounts.dto.report;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Set;

@Getter
@Setter
public class AccountDetail {

    private String cuenta;
    private BigDecimal saldoInicial;
    private String tipoCuenta;
    private boolean estado;

    private Set<MovementDetail> movimientos;

}
