package com.ecomarket.reportes.controller;
import com.ecomarket.reportes.model.EncargadoDTO;
import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.service.ReportesService;

import java.time.LocalDate;
import java.util.Arrays;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@WebMvcTest(ReportesController.class)
public class ReportesControllerTest {
    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReportesService reportesService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearReporte() throws Exception {
        EncargadoDTO encargado = new EncargadoDTO(null, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        Mockito.when(reportesService.crearReporte(Mockito.any(Reportes.class))).thenReturn(reporte);
        
        mockMvc.perform(post("/api/reportes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.tipo").value("Inventario"))
                .andExpect(jsonPath("$.fecha").value("2025-06-20"))
                .andExpect(jsonPath("$.descr_report").value("Problema con inventario producto"))
                .andExpect(jsonPath("$.email").value("alv.Duoc@gmail.com"))
                .andExpect(jsonPath("$.nombre").value("Alva"));
    }

    @Test
    void testListarReportes() throws Exception {
        Reportes reporte1 = new Reportes(1L, "Tipo1", LocalDate.now(), "Descripción 1", "a@correo.com", 1L, "Juan");
        Reportes reporte2 = new Reportes(2L, "Tipo2", LocalDate.now(), "Descripción 2", "b@correo.com", 2L, "Pedro");
        
        Mockito.when(reportesService.findAll()).thenReturn(Arrays.asList(reporte1, reporte2));
        mockMvc.perform(get("/api/reportes"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].tipo").value("Tipo1"))
                .andExpect(jsonPath("$[1].tipo").value("Tipo2"));
    }

    @Test
    void testGetById() throws Exception {
        EncargadoDTO encargado = new EncargadoDTO(null, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        Mockito.when(reportesService.findById(1L)).thenReturn(reporte);
        mockMvc.perform(get("/api/reportes/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Inventario"))
                .andExpect(jsonPath("$.fecha").value("2025-06-20"))
                .andExpect(jsonPath("$.descr_report").value("Problema con inventario producto"))
                .andExpect(jsonPath("$.email").value("alv.Duoc@gmail.com"))
                .andExpect(jsonPath("$.nombre").value("Alva"));
    }
    @Test
    void testUpdateById() throws Exception {
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", "correo@test.com", 1L , "Ana");
        Reportes actualizado = new Reportes(null, "Actualizado", LocalDate.of(2025, 6, 21), "Nueva descripción", "correo@test.com", 1L, "Ana");
        Mockito.when(reportesService.updateById(1L, reporte)).thenReturn(actualizado);
        mockMvc.perform(put("/api/reportes/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tipo").value("Actualizado"))
                .andExpect(jsonPath("$.fecha").value("2025-06-21"))
                .andExpect(jsonPath("$.descr_report").value("Nueva descripción"));
    }
    
    @Test
    void testDeleteById() throws Exception {
        EncargadoDTO encargado = new EncargadoDTO(null, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        Mockito.when(reportesService.findById(1L)).thenReturn(reporte);
        mockMvc.perform(delete("/api/reportes/eliminar/1"))
                .andExpect(status().isOk());
    }

    @Test
    void testDeleteByIdNotFound() throws Exception {
        Mockito.when(reportesService.findById(1L)).thenReturn(null);
        mockMvc.perform(delete("/api/reportes/eliminar/1"))
                .andExpect(status().isNotFound());
                

    }
    @Test
    void testGetByIdNotFound() throws Exception {
        Mockito.when(reportesService.findById(9999999L)).thenReturn(null);
        mockMvc.perform(get("/api/reportes/9999999"))
                .andExpect(status().isNotFound()); 
    }
    
    @Test
    void testGetAllNoContent() throws Exception {
        when(reportesService.findAll()).thenReturn(Arrays.asList());
        mockMvc.perform(get("/api/reportes"))
            .andExpect(status().isNoContent());
    }
    @Test
    void testCrearReporteConflict() throws Exception {
        EncargadoDTO encargado = new EncargadoDTO(null, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        when(reportesService.crearReporte(Mockito.any(Reportes.class))).thenThrow(new RuntimeException("Error al crear reporte"));
        mockMvc.perform(post("/api/reportes")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isConflict());
    }
    @Test
    void testUpdateByIdNotFound() throws Exception {
        EncargadoDTO encargado = new EncargadoDTO(null, "Alva", "Ro", "alv.Duoc@gmail.com");
        Reportes reporte = new Reportes(1L, "Inventario", LocalDate.parse("2025-06-20"), "Problema con inventario producto", encargado.getEmail(), encargado.getId(), encargado.getNombre());
        when(reportesService.updateById(1L, reporte)).thenReturn(null);
        mockMvc.perform(put("/api/reportes/actualizar/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(reporte)))
                .andExpect(status().isNotFound());
    }
}
