package com.ecomarket.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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


    @PostMapping("/crear")
    public Reportes crearReporte(@RequestBody Reportes reporte) {
        return reportesService.crearReporte(reporte);
    }
    
    @GetMapping("/{id}")
    public ResponseEntity<Reportes> getById(@PathVariable int id) {
        return new ResponseEntity<Reportes>(reportesService.findById(id), HttpStatus.OK);
    }

    @PutMapping("/actualizar/{id}")
    public ResponseEntity<Reportes> updateById(@PathVariable int id, @RequestBody Reportes reporte) {
        Reportes reporteUpd = reportesService.updateById(id, reporte);
        if (reporteUpd != null) {
            return new ResponseEntity<>(reporteUpd, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/eliminar/{id}")
    public ResponseEntity<String> deleteById(@PathVariable int id) {
        Reportes reporte = reportesService.findById(id);
        if (reporte != null) {
            reportesService.deleteById(id);
            return ResponseEntity.ok("Reporte DELETEADO");
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
}
