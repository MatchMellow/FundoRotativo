package com.fundorotativo.fundo;

public class Voluntario {

    private String nome;
    private String cpf;
    private String rg;
    private String endereco;
    private String bairro;

    // Construtor
    public Voluntario(String nome, String cpf, String rg, String endereco, String bairro) {
        this.nome = nome;
        this.cpf = cpf;
        this.rg = rg;
        this.endereco = endereco;
        this.bairro = bairro;
    }

    // Getters e Setters
    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getRg() {
        return rg;
    }

    public void setRg(String rg) {
        this.rg = rg;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

    public String getBairro() {
        return bairro;
    }

    public void setBairro(String bairro) {
        this.bairro = bairro;
    }

    // Método toString para salvar no arquivo (formato "nome,cpf,rg,endereco,bairro")
    @Override
    public String toString() {
        return nome + "," + cpf + "," + rg + "," + endereco + "," + bairro;
    }

    // Método para criar um voluntário a partir de uma linha lida do arquivo
    public static Voluntario fromString(String line) {
        String[] dados = line.split(",");
        return new Voluntario(dados[0], dados[1], dados[2], dados[3], dados[4]);
    }

    // Método toString para exibição legível (informações mais detalhadas)
    // Método toString para exibição legível (informações mais detalhadas)
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
