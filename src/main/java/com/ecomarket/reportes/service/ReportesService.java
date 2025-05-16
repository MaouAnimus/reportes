package com.ecomarket.reportes.service;

import java.util.List;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.repository.ReportesRepository;

@Service
public class ReportesService {
    @Autowired
    private ReportesRepository reportesRepository;


    public Reportes crearReporte(Reportes reporte) {
        return reportesRepository.save(reporte);    
    }


    public List<Reportes> findAll() {
        return reportesRepository.findAll();    
    }


    public Reportes findById(int id) {
        // TODO Auto-generated method stub
        return reportesRepository.findById(id);
    }
    
}
