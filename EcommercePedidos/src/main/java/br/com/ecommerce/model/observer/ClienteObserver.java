package br.com.ecommerce.model.observer;

public class ClienteObserver implements Observador {

    private String nome;

    public ClienteObserver(String nome) {
        this.nome = nome;
    }

    public void atualizar(String mensagem) {
        System.out.println(nome + " recebeu: " + mensagem);
    }
}