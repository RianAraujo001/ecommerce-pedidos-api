package br.com.ecommerce.model.state;

import br.com.ecommerce.model.entity.Pedido;

public class PagoState implements PedidoState {

    public void pagar(Pedido pedido) {
        throw new RuntimeException("Já está pago");
    }

    public void cancelar(Pedido pedido) {
        pedido.setStatus("CANCELADO");
    }

    public void enviar(Pedido pedido) {
        pedido.setStatus("ENVIADO");
    }
}