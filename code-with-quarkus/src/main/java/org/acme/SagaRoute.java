package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;

@ApplicationScoped
public class SagaRoute extends RouteBuilder {

    @Inject
    CreditoService creditoService;

    @Inject
    PedidoService pedidoService;

    @Override
    public void configure() throws Exception {

        CamelSagaService sagaService = new InMemorySagaService();
        getContext().addService(sagaService);

        from("direct:saga").saga().propagation(SagaPropagation.REQUIRES_NEW).log("Iniciando transação")
                .to("direct:newPedido").log("Criando pedido")
                .to("direct:newPedidoValor").log("Reservando crédito")
                .to("direct:finaliza").log("Feito!");

    }
}
