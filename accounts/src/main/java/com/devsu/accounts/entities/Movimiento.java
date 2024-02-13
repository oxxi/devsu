package com.devsu.accounts.entities;


import com.devsu.accounts.dto.MovementResponse;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;


@Entity
@Table(name = "movimientos")
@Getter
@Setter
public class Movimiento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "fecha")
    @CreationTimestamp()
    private LocalDateTime fecha;

    @Column(name = "tipo_movimiento")
    private String tipoMovimiento;

    @Column(name = "valor")
    private BigDecimal valor;

    @Column(name = "saldo")
    private BigDecimal saldo;

    @ManyToOne
    @JoinColumn(name = "cuenta_id", referencedColumnName = "id")
    private Cuenta cuenta;


    public MovementResponse toResponse() {
        return new MovementResponse(id, fecha, cuenta.getNumeroCuenta(), valor);
    }
}
