package com.fundorotativo.fundo;

public abstract class Pessoa {
    protected String nome;
    protected String cpf;
    protected String rg;
    protected String endereco;
    protected String bairro;

    // Construtor
    public Pessoa(String nome, String cpf, String rg, String endereco, String bairro) {
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

    // Método toString para exibição e persistência
    @Override
    public String toString() {
        return nome + "," + cpf + "," + rg + "," + endereco + "," + bairro;
    }
}
