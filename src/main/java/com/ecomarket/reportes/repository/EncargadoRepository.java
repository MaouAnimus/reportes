package com.ecomarket.reportes.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.ecomarket.reportes.model.EncargadoTienda;

@Repository

public interface EncargadoRepository extends JpaRepository<EncargadoTienda, Integer> {

    List<EncargadoTienda> findAll();

    @SuppressWarnings("unchecked")
    EncargadoTienda save(EncargadoTienda encargadoTienda);

    EncargadoTienda findById(int id);
    
}
