package br.com.petshop.servicos;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Servico;

public class Banho implements Servico {

    @Override
    public String executar(Pet pet) {
        return "Dando banho em " + pet.getNome() + "...";
    }
}

