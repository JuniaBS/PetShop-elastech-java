package br.com.petshop;

import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.TreeSet;

import br.com.petshop.dominio.Pet;
import br.com.petshop.dominio.Porte;
import br.com.petshop.dominio.Servico;
import br.com.petshop.fabrica.PetFactory;
import br.com.petshop.fila.FilaBanho;
import br.com.petshop.repositorio.MemoriaPetRepository;
import br.com.petshop.repositorio.PetRepository;
import br.com.petshop.servicos.Banho;
import br.com.petshop.servicos.Consulta;
import br.com.petshop.servicos.Tosa;
import br.com.petshop.servicos.Vacina;

public class PetShop {

    private static final PetFactory PET_FACTORY = new PetFactory();
    private static final List<Servico> SERVICOS = List.of(
            new Banho(),
            new Tosa(),
            new Consulta(),
            new Vacina()
    );

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        PetRepository repository = new MemoriaPetRepository();
        FilaBanho fila = new FilaBanho();

        int opcao = -1;

        while (opcao != 0) {
            mostrarMenu();

            try {
                opcao = sc.nextInt();
                sc.nextLine();

                switch (opcao) {
                    case 0:
                        System.out.println("Até logo!");
                        break;

                    case 1:
                        cadastrarPet(sc, repository);
                        break;

                    case 2:
                        listarPets(repository);
                        break;

                    case 3:
                        buscarPet(sc, repository);
                        break;

                    case 4:
                        mostrarEspecies(repository);
                        break;

                    case 5:
                        entrarNaFila(sc, fila, repository);
                        break;

                    case 6:
                        atenderProximo(fila);
                        break;

                    case 7:
                        executarServicos(sc, repository);
                        break;

                    case 8:
                        mostrarQuantidadePets(repository.contar());
                        break;

                    case 9:
                        filtrarPetsPorPorte(sc, repository);
                        break;

                    case 10:
                        atenderTodos(fila);
                        break;

                    default:
                        System.out.println("Opção inválida.");
                        break;
                }

            } catch (InputMismatchException e) {
                System.out.println("Digite apenas números!");
                sc.nextLine();
            }
        }

        sc.close();
    }

    public static void mostrarMenu() {
        System.out.println("\n===== PETSHOP =====");
        System.out.println("1 - Cadastrar pet");
        System.out.println("2 - Listar pets");
        System.out.println("3 - Buscar pet pelo nome");
        System.out.println("4 - Mostrar espécies cadastradas");
        System.out.println("5 - Entrar na fila de banho");
        System.out.println("6 - Atender próximo da fila");
        System.out.println("7 - Executar serviços do petshop");
        System.out.println("8 - Mostrar quantidade de pets cadastrados");
        System.out.println("9 - Filtrar pets por porte");
        System.out.println("10 - Atender todos os pets da fila");
        System.out.println("0 - Sair");
        System.out.print("Escolha: ");
    }

    public static void cadastrarPet(
            Scanner sc, PetRepository repository) {

        System.out.print("Nome do pet: ");
        String nome = sc.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("O nome do pet não pode ficar vazio.");
            return;
        }

        System.out.print("Espécie do pet: ");
        String especie = sc.nextLine().trim();

        if (especie.isEmpty()) {
            System.out.println("A espécie do pet não pode ficar vazia.");
            return;
        }

        System.out.print("Peso em kg: ");

        double peso;

        try {
            peso = Double.parseDouble(
                    sc.nextLine().trim().replace(",", ".")
            );
        } catch (NumberFormatException e) {
            System.out.println("Peso inválido! Digite um número.");
            return;
        }

        try {
            Pet novoPet = PET_FACTORY.criar(nome, especie, peso);

            repository.salvar(novoPet);

            System.out.println(
                    "Pet cadastrado! Agora são "
                            + repository.contar() + " pet(s)."
            );
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void listarPets(PetRepository repository) {
        if (repository.estaVazio()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }

        System.out.println("\n----- PETS CADASTRADOS -----");

        for (Pet pet : repository.listarTodos()) {
            mostrarFicha(pet, "FICHA DO PET");
            pet.emitirSom();
        }
    }

    public static void buscarPet(
            Scanner sc, PetRepository repository) {

        System.out.print("Qual pet você procura? ");
        String busca = sc.nextLine();

        boolean achou = false;

        for (Pet pet : repository.buscarPorNome(busca)) {
            System.out.println("\n---- PET ENCONTRADO ----");
            mostrarDadosPet(pet);
            achou = true;
        }

        if (!achou) {
            System.out.println("Pet não encontrado.");
        }
    }

    public static void mostrarDadosPet(Pet pet) {
        mostrarFicha(pet, "FICHA DO PET");
    }

    public static void mostrarFicha(Pet pet, String titulo) {
        System.out.println("=== " + titulo + " ===");
        System.out.println("Nome: " + pet.getNome());
        System.out.println("Espécie: " + pet.getEspecie());
        System.out.println("Peso: " + pet.getPeso() + " kg");
        System.out.println("Porte: " + pet.getPorte().getDescricao());
    }

    public static void mostrarEspecies(PetRepository repository) {
        if (repository.estaVazio()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }

        TreeSet<String> especies = new TreeSet<>();

        for (Pet pet : repository.listarTodos()) {
            especies.add(pet.getEspecie().toLowerCase().trim());
        }

        System.out.println("\n---- ESPÉCIES CADASTRADAS ----");

        for (String especie : especies) {
            System.out.println("- " + especie);
        }

        System.out.println(
                "Total de espécies diferentes: " + especies.size()
        );
    }

    public static void entrarNaFila(
            Scanner sc, FilaBanho fila, PetRepository repository) {

        System.out.print("Nome do pet pra entrar na fila: ");
        String nome = sc.nextLine().trim();

        if (nome.isEmpty()) {
            System.out.println("O nome do pet não pode ficar vazio.");
            return;
        }

        var petsEncontrados = repository.buscarPorNome(nome);

        if (petsEncontrados.isEmpty()) {
            System.out.println("Pet não encontrado.");
            return;
        }

        Pet pet = petsEncontrados.get(0);
        fila.adicionar(pet);

        System.out.println(pet.getNome() + " entrou na fila.");
        System.out.println("Fila agora: " + nomesDaFila(fila));
    }

    public static void atenderProximo(FilaBanho fila) {
        if (fila.estaVazia()) {
            System.out.println("A fila de banho está vazia.");
            return;
        }

        Pet proximo = fila.proximo();

        System.out.println("Próximo da fila: " + proximo.getNome());
        System.out.println("Atendendo " + proximo.getNome() + "...");

        System.out.println("Fila agora: " + nomesDaFila(fila));
    }

    public static void executarServicos(
            Scanner sc, PetRepository repository) {
        System.out.print("Nome do pet: ");
        String nomePet = sc.nextLine().trim();
        var petsEncontrados = repository.buscarPorNome(nomePet);

        if (petsEncontrados.isEmpty()) {
            System.out.println("Pet não encontrado.");
            return;
        }

        Pet pet = petsEncontrados.get(0);

        for (Servico servico : SERVICOS) {
            System.out.println(servico.executar(pet));
        }
    }

    public static void mostrarQuantidadePets(int total) {
        System.out.println("Total de pets cadastrados: " + total);
    }

    public static void filtrarPetsPorPorte(
            Scanner sc, PetRepository repository) {

        System.out.print("Qual porte deseja consultar? ");
        String porteBusca = sc.nextLine().trim();

        Porte porte;

        try {
            porte = Porte.aPartirDaDescricao(porteBusca);
        } catch (IllegalArgumentException e) {
            System.out.println("Porte inválido.");
            return;
        }

        var petsEncontrados = repository.buscarPorPorte(porte);

        for (Pet pet : petsEncontrados) {
            mostrarDadosPet(pet);
        }

        if (petsEncontrados.isEmpty()) {
            System.out.println(
                    "Nenhum pet encontrado para esse porte."
            );
        }
    }

    public static void atenderTodos(FilaBanho fila) {
        if (fila.estaVazia()) {
            System.out.println("A fila de banho está vazia!");
            return;
        }

        Pet pet;
        while ((pet = fila.proximo()) != null) {
            System.out.println("Atendendo: " + pet.getNome());
        }

        System.out.println("Todos os pets foram atendidos!");
    }

    private static String nomesDaFila(FilaBanho fila) {
        return fila.listar().stream()
                .map(Pet::getNome)
                .toList()
                .toString();
    }
}
