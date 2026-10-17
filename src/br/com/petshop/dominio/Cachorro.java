package br.com.petshop.dominio;

public class Cachorro extends Pet {

    public Cachorro(String nome, double peso) {
        super(nome, "Cachorro", peso);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " fez: Au au!");
    }
}
