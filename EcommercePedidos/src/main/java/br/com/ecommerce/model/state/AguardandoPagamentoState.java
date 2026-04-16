package br.com.ecommerce.model.state;

import br.com.ecommerce.model.entity.Pedido;

public class AguardandoPagamentoState implements PedidoState {

    public void pagar(Pedido pedido) {
        pedido.setStatus("PAGO");
    }

    public void cancelar(Pedido pedido) {
        pedido.setStatus("CANCELADO");
    }

    public void enviar(Pedido pedido) {
        throw new RuntimeException("Precisa pagar primeiro");
    }
}