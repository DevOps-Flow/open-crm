package com.devopsflow.opencrm.resource;

import com.devopsflow.opencrm.model.OrdemServico;
import com.devopsflow.opencrm.service.OrdemServicoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/ordens-servico")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class OrdemServicoResource {
    private final OrdemServicoService service;

    public OrdemServicoResource(OrdemServicoService service) {
        this.service = service;
    }

    @GET
    public List<OrdemServico> listAll() {
        return service.listAll();
    }

    @POST
    public Response create(OrdemServico ordemServico) {
        service.create(ordemServico);
        return Response.status(Response.Status.CREATED).entity(ordemServico).build();
    }

    @GET
    @Path("/{id}")
    public OrdemServico get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
