package com.fundorotativo.fundo;

public class Voluntario extends Pessoa {

    // Construtor
    public Voluntario(String nome, String cpf, String rg, String endereco, String bairro) {
        super(nome, cpf, rg, endereco, bairro); // Chamada ao construtor da superclasse
    }

    // Metodo para criar um voluntário a partir de uma string (útil ao ler o arquivo)
    public static Voluntario fromString(String line) {
        String[] dados = line.split(",");
        return new Voluntario(dados[0], dados[1], dados[2], dados[3], dados[4]);
    }

    // Método toString para exibição legível
    public String toStringExibicao() {
        return "Voluntário{" +
                "nome='" + nome + '\'' +
                ", cpf='" + cpf + '\'' +
                ", rg='" + rg + '\'' +
                ", endereco='" + endereco + '\'' +
                ", bairro='" + bairro + '\'' +
                '}';
    }
}
