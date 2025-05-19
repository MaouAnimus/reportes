package com.ecomarket.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.reportes.model.EncargadoTienda;
import com.ecomarket.reportes.service.EncargadoService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;






@RestController
@RequestMapping("/api/encargados")
public class EncargadoController {
    @Autowired
    private EncargadoService encargadoservice;

    @GetMapping()
    public ResponseEntity<List<EncargadoTienda>> getAll() {
    List<EncargadoTienda> encargados = encargadoservice.findAll();
    if (!encargados.isEmpty()) {
        return new ResponseEntity<>(encargados, HttpStatus.OK);
    } else {
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    }
    

    @PostMapping("/registrar")
    
    public ResponseEntity<EncargadoTienda> postEncargado(@RequestBody EncargadoTienda encargadoTienda) {
        
        EncargadoTienda et = encargadoservice.findById(encargadoTienda.getId_encargado());
        if (et == null) {
            return new ResponseEntity<>(encargadoservice.crearencargado(encargadoTienda), HttpStatus.CREATED);
        }else{
            return new ResponseEntity<>(HttpStatus.NOT_ACCEPTABLE);
        }
        
    }

    @GetMapping("/{id_encargado}")
    public ResponseEntity<EncargadoTienda> getById(@PathVariable int id_encargado) {
        return new ResponseEntity<EncargadoTienda>(encargadoservice.findById(id_encargado), HttpStatus.OK);
    }
    
    @PutMapping("/actualizar/{id}")
    public ResponseEntity<EncargadoTienda> updateById(@PathVariable int id, @RequestBody EncargadoTienda encargadoTienda) {
        EncargadoTienda enc = encargadoservice.updateById(id, encargadoTienda);
        if (enc != null) {
            return new ResponseEntity<>(enc, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
}
