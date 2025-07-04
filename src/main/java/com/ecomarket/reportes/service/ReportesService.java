package com.ecomarket.reportes.service;

import java.util.List;
import java.util.Collections;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.ecomarket.reportes.model.EncargadoDTO;
import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.repository.ReportesRepository;

@Service
public class ReportesService {
    @Autowired
    private ReportesRepository reportesRepository;

    @Autowired
    private RestTemplate restTemplate;

    public Reportes crearReporte(Reportes reporte) {
    String url = "http://localhost:8083/api/usuario/" + reporte.getId();
    EncargadoDTO encargado = restTemplate.getForObject(url, EncargadoDTO.class);
    if (encargado != null) {
        reporte.setId(encargado.getId());
        reporte.setNombre(encargado.getNombre());
        reporte.setEmail(encargado.getEmail());
        return reportesRepository.save(reporte);
    }
        throw new RuntimeException("No se encontró el encargado con ID: " + reporte.getId());
    }


    public List<Reportes> findAll() {
        return reportesRepository.findAll();    
    }

    public Reportes findById(Long id) {
        return reportesRepository.findById(id).orElse(null);
    }

    public Reportes updateById(Long id, Reportes reporte) {
        Reportes reporteExist = reportesRepository.findById(id).orElse(null);
        if (reporteExist != null) {
            if (reporte.getTipo() != null) {
                reporteExist.setTipo(reporte.getTipo());}
            if (reporte.getFecha() != null) {
                reporteExist.setFecha(reporte.getFecha());}
            if (reporte.getDescr_report() != null) {
                reporteExist.setDescr_report(reporte.getDescr_report());}
            reportesRepository.save(reporteExist);
            return reporteExist;
        }
        return null;    
    }

    public void deleteById(Long id) {
        Reportes reporte = reportesRepository.findById(id).orElse(null);
        if (reporte != null) {
            reportesRepository.delete(reporte);
        }
    }


    public List<Reportes> getAll() {
        List<Reportes> reportes = reportesRepository.findAll();
        if (reportes != null && !reportes.isEmpty()) 
        {
            return reportes;       
        }
        return Collections.emptyList();
    }
}
