package com.devsu.accounts.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class MovementRequest {

    @NotNull
    private String accountNumber;

    @NotNull
    private BigDecimal amount;

}
