package br.com.petshop.fabrica;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.function.BiFunction;

import br.com.petshop.dominio.Cachorro;
import br.com.petshop.dominio.Gato;
import br.com.petshop.dominio.Pet;

public class PetFactory {

    private final Map<String, BiFunction<String, Double, Pet>> criadores = new HashMap<>();

    public PetFactory() {
        registrar("cachorro", Cachorro::new);
        registrar("gato", Gato::new);
    }

    public void registrar(String especie, BiFunction<String, Double, Pet> criador) {
        if (especie == null || especie.isBlank()) {
            throw new IllegalArgumentException("A espécie não pode ficar vazia.");
        }

        if (criador == null) {
            throw new IllegalArgumentException("O criador não pode ser nulo.");
        }

        criadores.put(normalizar(especie), criador);
    }

    public Pet criar(String nome, String especie, double peso) {
        BiFunction<String, Double, Pet> criador = criadores.get(normalizar(especie));

        if (criador != null) {
            return criador.apply(nome, peso);
        }

        return new Pet(nome, especie.trim(), peso);
    }

    private String normalizar(String especie) {
        if (especie == null) {
            return "";
        }

        return especie.trim().toLowerCase(Locale.ROOT);
    }
}
