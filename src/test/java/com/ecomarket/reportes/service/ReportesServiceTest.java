package com.ecomarket.reportes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.web.client.RestTemplate;

import com.ecomarket.reportes.model.EncargadoDTO;
import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.repository.ReportesRepository;

public class ReportesServiceTest {
    @Mock
    private ReportesRepository reportesRepository;

    @Mock
    private RestTemplate restTemplate; 

    @InjectMocks
    private ReportesService reportesService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCrearReporte() {
        EncargadoDTO encargado = new EncargadoDTO(null, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        Reportes reporteGuardado = new Reportes(1L, "Inventario", LocalDate.of(2025, 6, 20), "Problema con inventario", "alv.Duoc@gmail.com", 1L, "Alva");
        when(reportesRepository.save(reporte)).thenReturn(reporteGuardado);
        Reportes resultado = reportesService.crearReporte(reporte);
        assertNotNull(resultado);
        assertEquals(reporteGuardado.getIdReporte(), resultado.getIdReporte());
        assertEquals(reporteGuardado.getTipo(), resultado.getTipo());
        assertEquals(reporteGuardado.getFecha(), resultado.getFecha());
        assertEquals(reporteGuardado.getDescr_report(), resultado.getDescr_report());
        assertEquals(reporteGuardado.getEmail(), resultado.getEmail());
        assertEquals(reporteGuardado.getId(), resultado.getId());
        assertEquals(reporteGuardado.getNombre(), resultado.getNombre());
        verify(reportesRepository).save(reporte);
    }
    
    @Test
    void testListarReportes() {
        Reportes reporte1 = new Reportes(1L, "Tipo1", LocalDate.now(), "Descripción 1", "a@correo.com", 1L, "Juan");
        Reportes reporte2 = new Reportes(2L, "Tipo2", LocalDate.now(), "Descripción 2", "b@correo.com", 2L, "Pedro");
        when(reportesRepository.findAll()).thenReturn(Arrays.asList(reporte1, reporte2));
        List<Reportes> resultado = reportesService.findAll();

        assertThat(resultado).hasSize(2).contains(reporte1, reporte2);
        verify(reportesRepository).findAll();
    }

    @Test
    void testFindById() {
        Reportes reporte1 = new Reportes(1L, "Tipo1", LocalDate.now(), "Descripción 1", "a@correo.com", 1L, "Juan");
        when(reportesRepository.findById(1L)).thenReturn(Optional.of(reporte1));
        Reportes resultado = reportesService.findById(1L);

        assertThat(resultado).isEqualTo(reporte1);
        verify(reportesRepository).findById(1L);
    }

    @Test
    void testUpdateById() {
        Reportes original = new Reportes(1L, "Original", LocalDate.of(2025, 6, 20), "Descripción original", "correo@test.com", 1L, "Ana");
        Reportes actualizado = new Reportes(null, "Actualizado", LocalDate.of(2025, 6, 21), "Nueva descripción", "correo@test.com", 1L, "Ana");
        when(reportesRepository.findById(1L)).thenReturn(Optional.of(original));
        
        Reportes resultado = reportesService.updateById(1L, actualizado);

        assertEquals("Actualizado", resultado.getTipo());
        assertEquals(LocalDate.of(2025, 6, 21), resultado.getFecha());
        assertEquals("Nueva descripción", resultado.getDescr_report());
        verify(reportesRepository).findById(1L);
        verify(reportesRepository).save(original);
    }

    @Test
    void testDeleteById() {
        Reportes reporte = new Reportes(1L, "Tipo", LocalDate.of(2025, 6, 20), "Descripción", "correo@test.com", 1L, "Ana");
        when(reportesRepository.findById(1L)).thenReturn(Optional.of(reporte));
        Reportes resultado = reportesService.findById(1L);
        assertThat(resultado).isNotNull();;
        reportesService.deleteById(1L);
        verify(reportesRepository).delete(reporte);
    }
}