package com.devopsflow.opencrm.resource;

import com.devopsflow.opencrm.model.Endereco;
import com.devopsflow.opencrm.service.EnderecoService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

import java.util.List;

@Path("/enderecos")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class EnderecoResource {
    private final EnderecoService service;

    public EnderecoResource(EnderecoService service) {
        this.service = service;
    }

    @GET
    public List<Endereco> listAll() {
        return service.listAll();
    }

    @POST
    public Response create(Endereco endereco) {
        service.create(endereco);
        return Response.status(Response.Status.CREATED).entity(endereco).build();
    }

    @GET
    @Path("/{id}")
    public Endereco get(@PathParam("id") Long id) {
        return service.get(id);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id) {
        service.delete(id);
        return Response.noContent().build();
    }
}
