package com.ecomarket.reportes.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ecomarket.reportes.model.EncargadoTienda;
import com.ecomarket.reportes.repository.EncargadoRepository;

@Service
public class EncargadoService {
    @Autowired
    private EncargadoRepository encargadoRepository;

    public EncargadoTienda findById(int id_encargado) {
        return encargadoRepository.findById(id_encargado);    
    }

    
    public EncargadoTienda crear_encargado(EncargadoTienda encargadoTienda) {
        return encargadoRepository.save(encargadoTienda);    
    }


    public List<EncargadoTienda> findAll() {
        // TODO Auto-generated method stub
        return encargadoRepository.findAll();    
    }
    
}
