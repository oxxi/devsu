package com.devsu.accounts.dto.report;


import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovementDetail {
    private LocalDateTime fecha;
    private BigDecimal saldo;
    private String movimiento;
    private BigDecimal monto;
}
