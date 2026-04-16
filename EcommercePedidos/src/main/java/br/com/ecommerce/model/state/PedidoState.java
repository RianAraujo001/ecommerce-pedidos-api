package br.com.ecommerce.model.state;

import br.com.ecommerce.model.entity.Pedido;

public interface PedidoState {
    void pagar(Pedido pedido);
    void cancelar(Pedido pedido);
    void enviar(Pedido pedido);
}