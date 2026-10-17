package br.com.petshop.dominio;

public class Pet {

    private final String nome;
    private final String especie;
    private final double peso;

    public Pet(String nome, String especie, double peso) {
        if (nome == null || nome.isBlank()) {
            throw new IllegalArgumentException("O nome do pet não pode ficar vazio.");
        }

        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("A espécie do pet não pode ficar vazia.");
        }

        if (peso <= 0 || Double.isInfinite(peso) || Double.isNaN(peso)) {
            throw new IllegalArgumentException("O peso deve ser maior que zero.");
        }

        this.nome = nome;
        this.especie = especie;
        this.peso = peso;
    }

    public String getNome() {
        return nome;
    }

    public String getEspecie() {
        return especie;
    }

    public double getPeso() {
        return peso;
    }

    public Porte getPorte() {
        return Porte.aPartirDoPeso(peso);
    }

    public void emitirSom() {
        System.out.println(nome + " fez um som.");
    }

}
