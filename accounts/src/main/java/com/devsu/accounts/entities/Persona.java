package com.devsu.accounts.entities;


import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;


@Entity
@Table(name = "persona")
@Getter
@Setter
@Inheritance(strategy = InheritanceType.JOINED)
public class Persona {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "nombre")
    private String nombre;

    @Column(name = "genero")
    private String gender;

    @Column(name = "edad")
    private Integer age;

    @Column(name = "identificacion")
    private String dni;

    @Column(name = "direccion")
    private String address;

    @Column(name = "telefono")
    private String phone;

}
