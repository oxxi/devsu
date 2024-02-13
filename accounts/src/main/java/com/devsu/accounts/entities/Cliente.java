package com.devsu.accounts.entities;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "cliente")
@Getter
@Setter
public class Cliente extends Persona {

    @Column(name = "contrasena")
    private String password;

    @Column(name = "estado")
    private Boolean status;
}
