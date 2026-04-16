package br.com.ecommerce.service;

import br.com.ecommerce.model.entity.Pedido;
import br.com.ecommerce.model.observer.ClienteObserver;
import br.com.ecommerce.model.strategy.*;
import br.com.ecommerce.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PedidoService {

    @Autowired
    private PedidoRepository repository;

    public Pedido criar(Pedido pedido, String tipoFrete) {

        CalculoFrete frete;

        if (tipoFrete.equalsIgnoreCase("CAMINHAO")) {
            frete = new FreteCaminhao();
        } else {
            frete = new FreteAviao();
        }

        pedido.setValorFrete(frete.calcular(pedido.getValorTotal()));
        pedido.setStatus("AGUARDANDO_PAGAMENTO");

        ClienteObserver observer = new ClienteObserver(pedido.getCliente());
        observer.atualizar("Pedido criado com sucesso");

        return repository.save(pedido);
    }

    public List<Pedido> listarTodos() {
        return repository.findAll();
    }

    public Pedido buscarPorId(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Pedido não encontrado"));
    }

    public Pedido pagar(Long id) {
        Pedido pedido = buscarPorId(id);

        if (!pedido.getStatus().equals("AGUARDANDO_PAGAMENTO")) {
            throw new RuntimeException("Pedido não pode ser pago");
        }

        pedido.setStatus("PAGO");

        return repository.save(pedido);
    }

    public Pedido enviar(Long id) {
        Pedido pedido = buscarPorId(id);

        if (!pedido.getStatus().equals("PAGO")) {
            throw new RuntimeException("Pedido precisa estar pago");
        }

        pedido.setStatus("ENVIADO");

        return repository.save(pedido);
    }

    public Pedido cancelar(Long id) {
        Pedido pedido = buscarPorId(id);

        if (pedido.getStatus().equals("ENVIADO")) {
            throw new RuntimeException("Não pode cancelar pedido enviado");
        }

        if (pedido.getStatus().equals("CANCELADO")) {
            throw new RuntimeException("Pedido já cancelado");
        }

        pedido.setStatus("CANCELADO");

        return repository.save(pedido);
    }
}