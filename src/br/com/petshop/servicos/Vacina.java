package br.com.petshop.servicos;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Servico;

public class Vacina implements Servico {

    @Override
    public String executar(Pet pet) {
        return "Aplicando vacina em " + pet.getNome() + "...";
    }
}

