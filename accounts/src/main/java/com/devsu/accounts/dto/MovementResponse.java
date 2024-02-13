package com.devsu.accounts.dto;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
public class MovementResponse extends MovementRequest{

    private Long id;

    private LocalDateTime createdAt;

    public MovementResponse(){}
    public MovementResponse(Long id, LocalDateTime fecha, String account, BigDecimal valor) {
       this.setCreatedAt(fecha);
       this.setId(id);
       this.setAmount(valor);
       this.setAccountNumber(account);
    }
}
