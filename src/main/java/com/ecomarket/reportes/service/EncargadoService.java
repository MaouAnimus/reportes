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

    
    public EncargadoTienda crearencargado(EncargadoTienda encargadoTienda) {
        return encargadoRepository.save(encargadoTienda);    
    }


    public List<EncargadoTienda> findAll() {
        return encargadoRepository.findAll();    
    }


    public EncargadoTienda updateById(int id, EncargadoTienda encargado) {
    EncargadoTienda encargadoExist = encargadoRepository.findById(id);
    if (encargadoExist != null) {
        if (encargado.getRut() != null) {
            encargadoExist.setRut(encargado.getRut());
        }
        if (encargado.getNombre() != null) {
            encargadoExist.setNombre(encargado.getNombre());
        }
        if (encargado.getApellido() != null) {
            encargadoExist.setApellido(encargado.getApellido());
        }
        if (encargado.getEmail() != null) {
            encargadoExist.setEmail(encargado.getEmail());
        }
        if (encargado.getPassword() != null) {
            encargadoExist.setPassword(encargado.getPassword());
        }
        encargadoRepository.save(encargadoExist);
        return encargadoExist;
    }
    return null;
}


    
    
}
