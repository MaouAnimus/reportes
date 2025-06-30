package com.ecomarket.reportes.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class EncargadoDTO {
    private Long id;
    private String nombre;
    private String apellido;
    private String email;
}
