package br.com.ecommerce.model.strategy;

public class FreteAviao implements CalculoFrete {
    public double calcular(double valor) {
        return valor * 0.10;
    }
}