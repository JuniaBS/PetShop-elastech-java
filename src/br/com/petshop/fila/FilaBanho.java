package br.com.petshop.fila;

import java.util.ArrayDeque;
import java.util.List;
import java.util.Queue;

import br.com.petshop.dominio.Pet;

public class FilaBanho {

    private final Queue<Pet> pets = new ArrayDeque<>();

    public void adicionar(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("O pet não pode ser nulo.");
        }

        pets.add(pet);
    }

    public Pet proximo() {
        return pets.poll();
    }

    public boolean estaVazia() {
        return pets.isEmpty();
    }

    public List<Pet> listar() {
        return List.copyOf(pets);
    }
}
