package com.ecomarket.reportes.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="reportes")
@Data
@NoArgsConstructor
@AllArgsConstructor

public class Reportes {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(length = 150,nullable = false)
    private String tipo;

    @Column(nullable = false)
    private LocalDate fecha;

    @Column(length = 250,nullable = false)
    private String descr_report;
}
