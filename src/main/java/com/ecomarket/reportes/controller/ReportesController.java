package com.ecomarket.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.service.ReportesService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/api/reportes")
public class ReportesController {
    @Autowired
    private ReportesService reportesService;

    @GetMapping()
    public ResponseEntity<List<Reportes>> getAll() {
    List<Reportes> reportes = reportesService.findAll();
    if (!reportes.isEmpty()) {
        return new ResponseEntity<>(reportes, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
        
    }


    @PostMapping("/crear")
    public Reportes crearReporte(@RequestBody Reportes reporte) {
        return reportesService.crearReporte(reporte);
    }
    


}
