package org.acme;

import jakarta.inject.Inject;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("credito")
public class CreditoResource {

    @Inject
    CreditoService service;

    @GET
    @Path("newPedidoValor")
    @Produces(MediaType.TEXT_PLAIN)
    public Response credito(@QueryParam("pedidoId") Long pedidoId, @QueryParam("valor") int valor) {
        try {
            service.newPedidoValor(pedidoId, valor);
            return Response.status(Response.Status.CREATED).build();
        } catch (Exception e) {
            throw new IllegalStateException("Não deu pra inserir novo pedido");
        }
    }

    @GET
    @Path("cancelPedidoValor")
    @Produces(MediaType.TEXT_PLAIN)
    public Response cancelPedidoValor(@QueryParam("id") Long id) {
        service.cancelPedidoValor(id);
        return Response.ok().build();
    }

    @GET
    @Path("getCreditoTotal")
    @Produces(MediaType.TEXT_PLAIN)
    public Response getCreditoTotal() {
        service.getCreditoTotal();
        return Response.ok().build();
    }
}
