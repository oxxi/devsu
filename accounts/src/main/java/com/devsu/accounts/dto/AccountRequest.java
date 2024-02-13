package com.devsu.accounts.dto;


import com.devsu.accounts.util.AccountTypes;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class AccountRequest {

    @NotNull
    private String accountNumber;
    @NotNull(message = "type is required")
    private AccountTypes type;
    @Min(value = 0, message = "Initial balance must be greater than 0")
    private BigDecimal initialBalance;
    @NotNull(message = "Client id is required")
    private Long clientId;
}
