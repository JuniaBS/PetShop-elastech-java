package br.com.petshop.repositorio;

import java.util.ArrayList;
import java.util.List;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Porte;

public class MemoriaPetRepository implements PetRepository {

    private final List<Pet> pets = new ArrayList<>();

    @Override
    public void salvar(Pet pet) {
        if (pet == null) {
            throw new IllegalArgumentException("O pet não pode ser nulo.");
        }

        pets.add(pet);
    }

    @Override
    public List<Pet> listarTodos() {
        return List.copyOf(pets);
    }

    @Override
    public List<Pet> buscarPorNome(String nome) {
        if (nome == null || nome.isBlank()) {
            return List.of();
        }

        return pets.stream()
                .filter(pet -> pet.getNome().equalsIgnoreCase(nome.trim()))
                .toList();
    }

    @Override
    public List<Pet> buscarPorPorte(Porte porte) {
        return pets.stream()
                .filter(pet -> pet.getPorte() == porte)
                .toList();
    }

    @Override
    public boolean estaVazio() {
        return pets.isEmpty();
    }

    @Override
    public int contar() {
        return pets.size();
    }
}
