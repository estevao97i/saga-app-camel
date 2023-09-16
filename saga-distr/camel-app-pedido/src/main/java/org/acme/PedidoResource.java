package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("pedido")
public class PedidoResource {

    @Inject
    PedidoService service;

    @GET
    @Path("newPedido")
    @Produces(MediaType.TEXT_PLAIN)
    public Response newPedido(@QueryParam("id") Long id) {
        service.newPedido(id);
        return Response.status(Response.Status.CREATED).entity(id).build();
    }

    @GET
    @Path("cancelPedido")
    @Produces(MediaType.TEXT_PLAIN)
    public Response cancelPedido(@QueryParam("id") Long id) {
        service.cancelPedido(id);

        return Response.noContent().entity(id).build();
    }


}
