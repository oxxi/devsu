package com.devsu.accounts.dto.report;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class Report {
    private String cliente;
    private LocalDate fecha;
    private LocalDate fechaFin;
    private Set<AccountDetail> cuentas;
}
