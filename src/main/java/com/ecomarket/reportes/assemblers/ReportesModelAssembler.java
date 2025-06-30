package com.ecomarket.reportes.assemblers;

import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.mvc.RepresentationModelAssemblerSupport;
import org.springframework.stereotype.Component;

import com.ecomarket.reportes.controller.ReportesController;
import com.ecomarket.reportes.model.Reportes;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

@Component
public class ReportesModelAssembler extends RepresentationModelAssemblerSupport<Reportes, EntityModel<Reportes>>{

    public ReportesModelAssembler() {
        super(ReportesController.class, (Class<EntityModel<Reportes>>) (Class<?>) EntityModel.class);
    }

    @Override
    public EntityModel<Reportes> toModel(Reportes reportes) {
        return EntityModel.of(reportes,
                linkTo(methodOn(ReportesController.class).getById(reportes.getId(), reportes)).withSelfRel(),
                linkTo(methodOn(ReportesController.class).getAll()).withRel("Reportes"));
    }
}
