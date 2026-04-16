package br.com.ecommerce.model.state;

import br.com.ecommerce.model.entity.Pedido;

public class CanceladoState implements PedidoState {

    public void pagar(Pedido pedido) {
        throw new RuntimeException("Pedido cancelado");
    }

    public void cancelar(Pedido pedido) {
        throw new RuntimeException("Já cancelado");
    }

    public void enviar(Pedido pedido) {
        throw new RuntimeException("Pedido cancelado");
    }
}