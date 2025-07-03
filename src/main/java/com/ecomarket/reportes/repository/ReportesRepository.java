package com.ecomarket.reportes.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import com.ecomarket.reportes.model.Reportes;

@Repository

public interface ReportesRepository extends JpaRepository<Reportes, Long>{
}
