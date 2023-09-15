package org.acme;

import jakarta.enterprise.context.ApplicationScoped;

import java.util.HashSet;
import java.util.Set;

@ApplicationScoped // para ser injetado em outras classes
public class PedidoService {

    private Set<Long> pedidos = new HashSet<>();

    public void newPedido(Long id) {
        pedidos.add(id);
    }

    public void cancelaPedido(Long id) {
        pedidos.remove(id);
    }
}
