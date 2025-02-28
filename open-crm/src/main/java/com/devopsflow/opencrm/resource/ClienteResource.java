package com.devopsflow.opencrm.resource;

import com.devopsflow.opencrm.model.Cliente;
import com.devopsflow.opencrm.service.ClienteService;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.UUID;

@Path("/clientes")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class ClienteResource {
    private final ClienteService service;

    private static final Logger LOG = LoggerFactory.getLogger(ClienteResource.class);

    public ClienteResource(ClienteService service) {
        this.service = service;
    }

    @GET
    public List<Cliente> listAll() {
        LOG.info("Listando todos os clientes");
        return service.listAll();
    }

    @POST
    public Response create(Cliente cliente) {
        service.create(cliente);
        return Response.status(Response.Status.CREATED).entity(cliente).build();
    }

    @GET
    @Path("/{id}")
    public Cliente get(@PathParam("id") UUID id) {
        return service.get(id);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") UUID id) {
        service.delete(id);
        return Response.noContent().build();
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") UUID id, Cliente cliente) {
        service.update(id, cliente);
        return Response.ok().entity(cliente).build();
    }
}
