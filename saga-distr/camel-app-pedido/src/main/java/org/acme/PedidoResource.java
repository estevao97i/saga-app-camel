package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;
import org.apache.camel.CamelContext;

@Path("pedido")
public class PedidoResource {

    @Inject
    PedidoService service;

    @Path("newPedido")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response newPedido(Long id) {
        service.newPedido(id);

        return Response.ok().build();
    }

    @Path("cancelPedido")
    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public Response cancelPedido(Long id) {
        service.cancelPedido(id);

        return Response.ok().build();
    }


}
