package br.com.ecommerce.controller;

import br.com.ecommerce.model.entity.Pedido;
import br.com.ecommerce.service.PedidoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    @Autowired
    private PedidoService service;

    @PostMapping
    public Pedido criar(@RequestBody Pedido pedido,
                        @RequestParam String frete) {
        return service.criar(pedido, frete);
    }

    @GetMapping
    public List<Pedido> listar() {
        return service.listarTodos();
    }

    @GetMapping("/{id}")
    public Pedido buscar(@PathVariable Long id) {
        return service.buscarPorId(id);
    }

    @PutMapping("/{id}/pagar")
    public Pedido pagar(@PathVariable Long id) {
        return service.pagar(id);
    }

    @PutMapping("/{id}/enviar")
    public Pedido enviar(@PathVariable Long id) {
        return service.enviar(id);
    }

    @PutMapping("/{id}/cancelar")
    public Pedido cancelar(@PathVariable Long id) {
        return service.cancelar(id);
    }
}