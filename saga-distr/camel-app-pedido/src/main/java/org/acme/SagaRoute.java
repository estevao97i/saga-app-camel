package org.acme;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.apache.camel.Exchange;
import org.apache.camel.builder.RouteBuilder;
import org.apache.camel.model.SagaPropagation;
import org.apache.camel.saga.CamelSagaService;
import org.apache.camel.saga.InMemorySagaService;

@ApplicationScoped
public class SagaRoute extends RouteBuilder {
    @Inject
    PedidoService pedidoService;

    @Override
    public void configure() throws Exception {

        CamelSagaService sagaService = new InMemorySagaService();
        getContext().addService(sagaService);

        // Saga
        from("direct:saga").saga().propagation(SagaPropagation.REQUIRES_NEW).log("Iniciando transação")
                .to("direct:newPedido").log("Criando pedido com id ${header.id}")
                .to("direct:newPedidoValor").log("Reservando crédito")
                .to("direct:finaliza").log("Feito!");

        // Pedido service
        from("direct:newPedido").saga().propagation(SagaPropagation.MANDATORY)
                .compensation("direct:cancelPedido")
                .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                .bean(pedidoService, "newPedido").log("Pedido ${body} Criado");

        from("direct:cancelPedido").transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
                .bean(pedidoService, "cancelPedido").log("Pedido ${body} cancelado");

        // Credito Service
//        from("direct:newPedidoValor").saga().propagation(SagaPropagation.MANDATORY)
//                .compensation("direct:cancelPedidoValor")
//                .transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
//                .bean(creditoService, "newPedidoValor").
//                log("Credito no pedido ${header.pedidoId} no valor de ${header.valor} concedido para a saga ${body} ");
//
//        from("direct:cancelPedidoValor").transform().header(Exchange.SAGA_LONG_RUNNING_ACTION)
//                .bean(creditoService, "cancelPedidoValor")
//                .log("Credito no pedido ${header.pedidoId} no valor de ${header.valor} cancelado para a saga ${body} ");

        // Finaliza
        from("direct:finaliza").saga().propagation(SagaPropagation.MANDATORY)
                .choice()
                .end();
    }
}
