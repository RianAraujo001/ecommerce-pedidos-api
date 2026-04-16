package br.com.ecommerce.model.strategy;

public class FreteCaminhao implements CalculoFrete {
    public double calcular(double valor) {
        return valor * 0.05;
    }
}