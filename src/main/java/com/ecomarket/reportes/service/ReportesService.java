package com.ecomarket.reportes.service;

import java.util.List;


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
    // Suponiendo que el reporte tiene un getId() que retorna el id del encargado
    String url = "http://localhost:8081/api/usuario/" + reporte.getId();
    EncargadoDTO encargado = restTemplate.getForObject(url, EncargadoDTO.class);
    if (encargado != null) {
        reporte.setEmail(encargado.getEmail());
    }
    return reportesRepository.save(reporte);
}


    public List<Reportes> findAll() {
        return reportesRepository.findAll();    
    }


    public Reportes findById(int id) {
        return reportesRepository.findById(id);
    }


    public Reportes updateById(int id, Reportes reporte) {
        Reportes reporteExist = reportesRepository.findById(id);
        if (reporteExist != null) {
            if (reporteExist.getTipo() != null) {
                reporteExist.setTipo(reporte.getTipo());
            }
            if (reporteExist.getFecha() != null) {
                reporteExist.setFecha(reporte.getFecha());
            }
            if (reporteExist.getDescr_report() != null) {
                reporteExist.setDescr_report(reporte.getDescr_report());
            }
            reportesRepository.save(reporteExist);
            return reporteExist;
        }
        return null;    
    }


    public Reportes deleteById(int id) {
        Reportes reporte = reportesRepository.findById(id);
        if (reporte != null) {
            reportesRepository.delete(reporte);
            return reporte;
        }
        return null;
    }
    
}
