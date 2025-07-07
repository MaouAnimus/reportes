package com.ecomarket.reportes.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.never;
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
        EncargadoDTO encargado = new EncargadoDTO(1L, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        Reportes reporteGuardado = new Reportes(1L, "Inventario", LocalDate.of(2025, 6, 20), "Problema con inventario", "alv.Duoc@gmail.com", 1L, "Alva");
        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(encargado);
        when(reportesRepository.save(any(Reportes.class))).thenReturn(reporteGuardado);
        Reportes resultado = reportesService.crearReporte(reporte);
        assertNotNull(resultado);
        assertEquals(reporteGuardado.getIdReporte(), resultado.getIdReporte());
        assertEquals(reporteGuardado.getTipo(), resultado.getTipo());
        assertEquals(reporteGuardado.getFecha(), resultado.getFecha());
        assertEquals(reporteGuardado.getDescr_report(), resultado.getDescr_report());
        assertEquals(reporteGuardado.getEmail(), resultado.getEmail());
        assertEquals(reporteGuardado.getId(), resultado.getId());
        assertEquals(reporteGuardado.getNombre(), resultado.getNombre());
        verify(restTemplate).getForObject(any(String.class), any(Class.class));
        verify(reportesRepository).save(any(Reportes.class));
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
    
    @Test
    void testGetAll() {
        Reportes reporte = new Reportes(1L, "Tipo", LocalDate.of(2025, 6, 20), "Descripción", "correo@test.com", 1L, "Ana");
        when(reportesRepository.findAll()).thenReturn(Arrays.asList(reporte));
        List<Reportes> resultado = reportesService.getAll();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0)).isEqualTo(reporte);
        verify(reportesRepository).findAll(); 
    } 

    @Test
    void testGetAllEmpty() {
        when(reportesRepository.findAll()).thenReturn(Arrays.asList());
        List<Reportes> resultado = reportesService.getAll();
        assertThat(resultado).isEmpty();
        verify(reportesRepository).findAll();
    }
    @Test
    void testGetAllNull() {
        when(reportesRepository.findAll()).thenReturn(null);
        List<Reportes> resultado = reportesService.getAll();
        assertThat(resultado).isEmpty();
        verify(reportesRepository).findAll();
    }

    @Test
    void testGetAllWithNullValues() {
        Reportes reporte = new Reportes(1L, null, null, null, null, null, null);
        when(reportesRepository.findAll()).thenReturn(Arrays.asList(reporte));
        List<Reportes> resultado = reportesService.getAll();
        assertThat(resultado).isNotEmpty();
        assertThat(resultado.get(0).getTipo()).isNull();
        assertThat(resultado.get(0).getFecha()).isNull();
        assertThat(resultado.get(0).getDescr_report()).isNull();
        assertThat(resultado.get(0).getEmail()).isNull();
        assertThat(resultado.get(0).getId()).isNull();
        assertThat(resultado.get(0).getNombre()).isNull();
        verify(reportesRepository).findAll();
    }

    @Test
    void testUpdateByIdNotFound(){
        Reportes reporte = new Reportes(1L, "Tipo", LocalDate.of(2025, 6, 20), "Descripción", "correo@test.com", 1L, "Ana");
        when(reportesRepository.findById(1L)).thenReturn(Optional.empty());
        Reportes resultado = reportesService.updateById(1L, reporte);
        assertThat(resultado).isNull();
        verify(reportesRepository).findById(1L);
    }
    
    @Test
    void testDeleteByIdNotFound() {
    when(reportesRepository.findById(1L)).thenReturn(Optional.empty());

    reportesService.deleteById(1L);
    verify(reportesRepository).findById(1L);
    verify(reportesRepository, never()).delete(any()); 
    verify(reportesRepository, never()).deleteById(any()); 
    }
    @Test
    void testCrearReporteWithNullEncargado() {
        Reportes reporte = new Reportes(1L, "Tipo", LocalDate.of(2025, 6, 20), "Descripción", "correo@test.com", null, null);
        when(restTemplate.getForObject(any(String.class), any(Class.class))).thenReturn(null);
        assertThrows(RuntimeException.class, () -> {
        reportesService.crearReporte(reporte);
    });
        verify(restTemplate).getForObject(any(String.class), any(Class.class));
        verify(reportesRepository, never()).save(any());
    }
    
    @Test
    void testUpdateByIdWithNullFields() {
    Reportes original = new Reportes(1L, "Original", LocalDate.of(2025, 6, 20), "Descripción original", "correo@test.com", 1L, "Ana");
    Reportes actualizado = new Reportes(null, null, null, null, "correo@test.com", 1L, "Ana");
    when(reportesRepository.findById(1L)).thenReturn(Optional.of(original));

    Reportes resultado = reportesService.updateById(1L, actualizado);

    assertEquals("Original", resultado.getTipo());
    assertEquals(LocalDate.of(2025, 6, 20), resultado.getFecha());
    assertEquals("Descripción original", resultado.getDescr_report());
    verify(reportesRepository).save(original);
    }
}