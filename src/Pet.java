public class Pet {

    String nome;
    String especie;
    String porte;
    double peso;

    String definirPorte()
    {
        if (peso < 10) {
            return "pequeno";
        } else if (peso >= 10 && peso < 25) {
            return "médio";
        } else {
            return "grande";
        }
    }

    void emitirSom() {
        System.out.println(nome + " fez um som.");
    }

}
