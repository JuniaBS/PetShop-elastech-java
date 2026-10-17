package br.com.petshop.repositorio;

import java.util.List;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Porte;

public interface PetRepository {

    void salvar(Pet pet);

    List<Pet> listarTodos();

    List<Pet> buscarPorNome(String nome);

    List<Pet> buscarPorPorte(Porte porte);

    boolean estaVazio();

    int contar();
}
