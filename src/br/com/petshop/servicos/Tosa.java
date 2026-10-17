package br.com.petshop.servicos;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Servico;

public class Tosa implements Servico {

    @Override
    public String executar(Pet pet) {
        return "Tosando " + pet.getNome() + "...";
    }
}
