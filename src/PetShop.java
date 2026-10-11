import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class PetShop {

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        ArrayList<Pet> lista = new ArrayList<>();
        ArrayDeque<String> fila = new ArrayDeque<>();

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
                        cadastrarPet(sc, lista);
                        break;

                    case 2:
                        listarPets(lista);
                        break;

                    case 3:
                        buscarPet(sc, lista);
                        break;

                    case 4:
                        mostrarEspecies(lista);
                        break;

                    case 5:
                        entrarNaFila(sc, fila);
                        break;

                    case 6:
                        atenderProximo(fila);
                        break;

                    case 7:
                        executarServicos(sc);
                        break;

                    case 8:
                        mostrarQuantidadePets(lista.size());
                        break;

                    case 9:
                        filtrarPetsPorPorte(sc, lista);
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
            Scanner sc, ArrayList<Pet> lista) {

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

        Pet novoPet;

        if (especie.equalsIgnoreCase("Cachorro")) {
            novoPet = new Cachorro();
            especie = "Cachorro";
        } else if (especie.equalsIgnoreCase("Gato")) {
            novoPet = new Gato();
            especie = "Gato";
        } else {
            novoPet = new Pet();
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

        if (peso <= 0 || Double.isInfinite(peso)
                || Double.isNaN(peso)) {
            System.out.println("O peso deve ser maior que zero.");
            return;
        }

        novoPet.nome = nome;
        novoPet.especie = especie;
        novoPet.peso = peso;
        novoPet.porte = novoPet.definirPorte();

        lista.add(novoPet);

        System.out.println(
                "Pet cadastrado! Agora são "
                        + lista.size() + " pet(s)."
        );
    }

    public static void listarPets(ArrayList<Pet> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }

        System.out.println("\n----- PETS CADASTRADOS -----");

        for (Pet pet : lista) {
            mostrarFicha(pet);
            mostrarFicha(pet, "FICHA DO PET");
            pet.emitirSom();
        }
    }

    public static void buscarPet(
            Scanner sc, ArrayList<Pet> lista) {

        System.out.print("Qual pet você procura? ");
        String busca = sc.nextLine();

        boolean achou = false;

        for (Pet pet : lista) {
            if (pet.nome.equalsIgnoreCase(busca)) {
                System.out.println("\n---- PET ENCONTRADO ----");
                mostrarDadosPet(pet);
                achou = true;
            }
        }

        if (!achou) {
            System.out.println("Pet não encontrado.");
        }
    }

    public static void mostrarDadosPet(Pet pet) {
        System.out.println("==== FICHA DO PET ====");
        System.out.println(
                "Nome: " + pet.nome
                        + ", Espécie: " + pet.especie
                        + ", Peso: " + pet.peso + " kg"
                        + ", Porte: " + pet.porte
        );
    }

    public static void mostrarFicha(Pet pet) {
        System.out.println("Nome: " + pet.nome);
    }

    public static void mostrarFicha(Pet pet, String titulo) {
        System.out.println("=== " + titulo + " ===");
        System.out.println("Nome: " + pet.nome);
        System.out.println("Espécie: " + pet.especie);
        System.out.println("Peso: " + pet.peso + " kg");
        System.out.println("Porte: " + pet.porte);
    }

    public static void mostrarEspecies(ArrayList<Pet> lista) {
        if (lista.isEmpty()) {
            System.out.println("Nenhum pet cadastrado.");
            return;
        }

        HashSet<String> especies = new HashSet<>();

        for (Pet pet : lista) {
            especies.add(pet.especie.toLowerCase().trim());
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
            Scanner sc, ArrayDeque<String> fila) {

        System.out.print("Nome do pet pra entrar na fila: ");
        String nome = sc.nextLine();

        fila.add(nome);

        System.out.println(nome + " entrou na fila.");
        System.out.println("Fila agora: " + fila);
    }

    public static void atenderProximo(ArrayDeque<String> fila) {
        if (fila.isEmpty()) {
            System.out.println("A fila de banho está vazia.");
            return;
        }

        String proximo = fila.peek();

        System.out.println("Próximo da fila: " + proximo);
        System.out.println("Atendendo " + proximo + "...");

        fila.poll();

        System.out.println("Fila agora: " + fila);
    }

    public static void executarServicos(Scanner sc) {
        System.out.print("Nome do pet: ");
        String nomePet = sc.nextLine();

        ArrayList<Servico> servicos = new ArrayList<>();

        servicos.add(new Banho());
        servicos.add(new Tosa());
        servicos.add(new Consulta());
        servicos.add(new Vacina());

        for (Servico servico : servicos) {
            servico.executar(nomePet);
        }
    }

    public static void mostrarQuantidadePets(int total) {
        System.out.println("Total de pets cadastrados: " + total);
    }

    public static void filtrarPetsPorPorte(
            Scanner sc, ArrayList<Pet> lista) {

        System.out.print("Qual porte deseja consultar? ");
        String porteBusca = sc.nextLine().trim();

        boolean encontrou = false;

        for (Pet pet : lista) {
            if (pet.porte.equalsIgnoreCase(porteBusca)) {
                mostrarDadosPet(pet);
                encontrou = true;
            }
        }

        if (!encontrou) {
            System.out.println(
                    "Nenhum pet encontrado para esse porte."
            );
        }
    }

    public static void atenderTodos(ArrayDeque<String> fila) {
        if (fila.isEmpty()) {
            System.out.println("A fila de banho está vazia!");
            return;
        }

        while (!fila.isEmpty()) {
            String pet = fila.poll();
            System.out.println("Atendendo: " + pet);
        }

        System.out.println("Todos os pets foram atendidos!");
    }
}
