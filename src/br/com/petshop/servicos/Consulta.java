package br.com.petshop.servicos;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Servico;

public class Consulta implements Servico {

    @Override
    public String executar(Pet pet) {
        return "Levando " + pet.getNome() + " para a consulta...";
    }
}
