package com.ecomarket.reportes.model;

import jakarta.persistence.Id;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor

@Table(name = "encargado_tienda")

public class EncargadoTienda {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(unique=true)
    private int id_encargado;

    @Column(length = 10, nullable = false, unique = true)
    private String rut;

    @Column(length = 50, nullable = false)
    private String nombre;

    @Column(length = 50, nullable = false)
    private String apellido;

    @Column(length = 50, nullable = false, unique = true)
    private String email;

    @Column(length = 50, nullable = false)
    private String password;
}
