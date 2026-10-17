package br.com.petshop.dominio;

public class Gato extends Pet {

    public Gato(String nome, double peso) {
        super(nome, "Gato", peso);
    }

    @Override
    public void emitirSom() {
        System.out.println(getNome() + " fez: Miau!");
    }
}
