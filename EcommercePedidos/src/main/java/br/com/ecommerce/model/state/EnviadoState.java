package br.com.ecommerce.model.state;

import br.com.ecommerce.model.entity.Pedido;

public class EnviadoState implements PedidoState {

    public void pagar(Pedido pedido) {
        throw new RuntimeException("Já enviado");
    }

    public void cancelar(Pedido pedido) {
        throw new RuntimeException("Não pode cancelar");
    }

    public void enviar(Pedido pedido) {
        throw new RuntimeException("Já enviado");
    }
}