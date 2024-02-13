package com.devsu.client.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ClientCreateDto {

    private Long id;
    @NotNull
    private String nombre;
    private String direccion;
    @NotNull
    private String telefono;
    @NotEmpty
    private String contrasena;
    private Boolean estado;
}
