package br.com.petshop.dominio;

import java.util.Locale;

public enum Porte {
    PEQUENO("pequeno"),
    MEDIO("médio"),
    GRANDE("grande");

    private final String descricao;

    Porte(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    public static Porte aPartirDoPeso(double peso) {
        if (peso < 10) {
            return PEQUENO;
        }

        if (peso < 25) {
            return MEDIO;
        }

        return GRANDE;
    }

    public static Porte aPartirDaDescricao(String descricao) {
        if (descricao == null) {
            throw new IllegalArgumentException("Porte inválido.");
        }

        String valor = descricao.trim().toLowerCase(Locale.ROOT);

        for (Porte porte : values()) {
            if (porte.descricao.equals(valor)
                    || porte.name().equalsIgnoreCase(valor)
                    || (porte == MEDIO && "medio".equals(valor))) {
                return porte;
            }
        }

        throw new IllegalArgumentException("Porte inválido.");
    }
}
