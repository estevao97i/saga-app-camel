package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashMap;
import java.util.Map;

@ApplicationScoped // para injetar essa classe em outra
public class CreditoService {

    private int creditoTotal;
    private Map<Long, Integer> pedido_valor = new HashMap<>();

    public CreditoService() {
        creditoTotal = 100;
    }

    public void newPedidoValor(Long pedidoId, int valor) {
        if (valor > creditoTotal) {
            throw new IllegalStateException("Saldo insuficiente");
        }
        creditoTotal = creditoTotal - valor;
        pedido_valor.put(pedidoId, valor);
    }

    public void cancelPedidoValor(Long id) {
        creditoTotal = creditoTotal + pedido_valor.get(id);
        pedido_valor.remove(id);
    }

    public int getCreditoTotal() {
        return creditoTotal;
    }
}
