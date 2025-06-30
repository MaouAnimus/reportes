package com.ecomarket.reportes.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.MediaTypes;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ecomarket.reportes.model.Reportes;
import com.ecomarket.reportes.service.ReportesService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;



@RestController
@RequestMapping("/api/hateoas")
public class ReportesControllerV2 {
    @Autowired
    private ReportesService reportesService;
    
    @GetMapping(value = "/listar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Reportes>>> listarReportesConLinks() {
        List<Reportes> reportes = reportesService.getAll();
        if (reportes == null || reportes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        List<EntityModel<Reportes>> reportesModels = reportes.stream()
                .map(rep -> EntityModel.of(rep,
                        linkTo(methodOn(ReportesControllerV2.class).buscarReporte(rep.getId(), null)).withSelfRel()))
                        .collect(java.util.stream.Collectors.toList());
        
        CollectionModel<EntityModel<Reportes>> collectionModel = CollectionModel.of(
            reportesModels,
            linkTo(methodOn(ReportesControllerV2.class).listarReportesConLinks()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }

    @GetMapping(value = "/buscar/{id}", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<EntityModel<Reportes>> buscarReporteporId(@PathVariable Long id) {
        Reportes reporte = reportesService.findById(id);
        if (reporte == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
        EntityModel<Reportes> reportesModel = EntityModel.of(reporte,
                linkTo(methodOn(ReportesControllerV2.class).buscarReporteporId(id)).withSelfRel(),
                linkTo(methodOn(ReportesControllerV2.class).listarReportesConLinks()).withRel("reportes"));
        
        return ResponseEntity.ok(reportesModel);
    }
    
    @GetMapping(value = "/buscar", produces = MediaTypes.HAL_JSON_VALUE)
    public ResponseEntity<CollectionModel<EntityModel<Reportes>>> buscarReportes() {
        List<Reportes> reportes = reportesService.getAll();
        if (reportes == null || reportes.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        List<EntityModel<Reportes>> reportesModels = reportes.stream()
                .map(reporte -> EntityModel.of(reporte,
                        linkTo(methodOn(ReportesControllerV2.class).buscarReporteporId(reporte.getId())).withSelfRel()))
                        .collect(java.util.stream.Collectors.toList());

        CollectionModel<EntityModel<Reportes>> collectionModel = CollectionModel.of(reportesModels,
        linkTo(methodOn(ReportesControllerV2.class).buscarReportes()).withSelfRel());
        
        return ResponseEntity.ok(collectionModel);
    }
    


    public ResponseEntity<EntityModel<Reportes>> buscarReporte(Long id, Object unused) {
        return null;
    }
    
}
