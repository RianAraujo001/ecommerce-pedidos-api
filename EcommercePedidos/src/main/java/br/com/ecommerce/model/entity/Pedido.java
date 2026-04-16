package br.com.ecommerce.model.entity;

import jakarta.persistence.*;

@Entity
public class Pedido {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String cliente;
    private double valorTotal;
    private double valorFrete;
    private String status;

    public Long getId() { return id; }
    public String getCliente() { return cliente; }
    public double getValorTotal() { return valorTotal; }
    public double getValorFrete() { return valorFrete; }
    public String getStatus() { return status; }

    public void setId(Long id) { this.id = id; }
    public void setCliente(String cliente) { this.cliente = cliente; }
    public void setValorTotal(double valorTotal) { this.valorTotal = valorTotal; }
    public void setValorFrete(double valorFrete) { this.valorFrete = valorFrete; }
    public void setStatus(String status) { this.status = status; }
}