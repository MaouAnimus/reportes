package com.ecomarket.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.service.ReportesService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;



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

    @PostMapping
    public ResponseEntity<Reportes> crearReporte(@RequestBody Reportes reporte) {
        try {
            return new ResponseEntity<>(reportesService.crearReporte(reporte),HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.CONFLICT);
        }
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reportes> getById(@PathVariable Long id, @RequestBody(required = false) Reportes reportes) {
        Reportes reporte = reportesService.findById(id);
        if (reporte == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(reporte, HttpStatus.OK);
}

    

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Reportes> updateById(@PathVariable Long id, @RequestBody Reportes reporte) {
        Reportes reporteUpd = reportesService.updateById(id, reporte);
        if (reporteUpd != null) {
            return new ResponseEntity<>(reporteUpd, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }   
    }

    @DeleteMapping("/eliminar/{id}")
        public void deleteById(@PathVariable Long id) {
        Reportes reporte = reportesService.findById(id);
        if (reporte != null) {
            reportesService.deleteById(id);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
    }
}
    
    
}
