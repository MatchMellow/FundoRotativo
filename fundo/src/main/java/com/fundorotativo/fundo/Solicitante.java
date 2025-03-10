package com.fundorotativo.fundo;

public class Solicitante extends Pessoa {

    // Construtor
    public Solicitante(String nome, String cpf, String rg, String endereco, String bairro) {
        super(nome, cpf, rg, endereco, bairro); // Chamada ao construtor da superclasse
    }

    // Metodo para criar um solicitante a partir de uma string (útil ao ler o arquivo)
    public static Solicitante fromString(String line) {
        String[] dados = line.split(",");
        return new Solicitante(dados[0], dados[1], dados[2], dados[3], dados[4]);
    }
}
