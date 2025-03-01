package com.fundorotativo.fundo;

import javafx.beans.property.*;

public class Contrato {

    private final StringProperty nome;
    private final StringProperty parcela;
    private final StringProperty data;
    private final DoubleProperty valorParcela;
    private final BooleanProperty pago;
    private final IntegerProperty idEmprestimo;  // Novo campo para vincular o contrato ao empréstimo

    // Construtor
    public Contrato(String nome, String parcela, String data, double valorParcela, int idEmprestimo) {
        this.nome = new SimpleStringProperty(nome);
        this.parcela = new SimpleStringProperty(parcela);
        this.data = new SimpleStringProperty(data);
        this.valorParcela = new SimpleDoubleProperty(valorParcela);
        this.pago = new SimpleBooleanProperty(false);  // Inicialmente o contrato não está pago
        this.idEmprestimo = new SimpleIntegerProperty(idEmprestimo);  // Atribuindo o ID do empréstimo
    }

    // Método fromString para criar um Contrato a partir de uma linha do arquivo
    public static Contrato fromString(String linha) {
        // Assume que os dados na linha estão separados por vírgulas
        String[] dados = linha.split(",");

        // Certifique-se de que há dados suficientes para criar um Contrato
        if (dados.length >= 5) {
            // Criando o Contrato com os dados da linha
            String nome = dados[0].trim();
            String parcela = dados[1].trim();
            String data = dados[2].trim();
            double valorParcela = Double.parseDouble(dados[3].trim());
            int idEmprestimo = Integer.parseInt(dados[4].trim());  // Adicionando o ID do empréstimo

            // Criar e retornar o objeto Contrato
            Contrato contrato = new Contrato(nome, parcela, data, valorParcela, idEmprestimo);

            // Se desejar, pode fazer a conversão do campo "pago" aqui
            if (dados.length > 5) {
                contrato.setPago(Boolean.parseBoolean(dados[5].trim()));
            }

            return contrato;
        }
        return null;  // Se os dados estiverem faltando, retorne null
    }

    // Getters e Setters
    public final String getNome() {
        return nome.get();
    }

    public final void setNome(String nome) {
        this.nome.set(nome);
    }

    public final StringProperty nomeProperty() {
        return nome;
    }

    public final String getParcela() {
        return parcela.get();
    }

    public final void setParcela(String parcela) {
        this.parcela.set(parcela);
    }

    public StringProperty parcelaProperty() {
        return parcela;
    }

    public String getData() {
        return data.get();
    }

    public void setData(String data) {
        this.data.set(data);
    }

    public StringProperty dataProperty() {
        return data;
    }

    public double getValorParcela() {
        return valorParcela.get();
    }

    public void setValorParcela(double valorParcela) {
        this.valorParcela.set(valorParcela);
    }

    public DoubleProperty valorParcelaProperty() {
        return valorParcela;
    }

    // Novo getter e setter para o campo "pago"
    public boolean isPago() {
        return pago.get();
    }

    public void setPago(boolean pago) {
        this.pago.set(pago);
    }

    public BooleanProperty pagoProperty() {
        return pago;
    }

    // Novo getter e setter para o ID do Empréstimo
    public int getIdEmprestimo() {
        return idEmprestimo.get();
    }

    public void setIdEmprestimo(int idEmprestimo) {
        this.idEmprestimo.set(idEmprestimo);
    }

    public IntegerProperty idEmprestimoProperty() {
        return idEmprestimo;
    }

    // Método toString
    @Override
    public String toString() {
        return nome.get() + "," + parcela.get() + "," + data.get() + "," + valorParcela.get() + "," + idEmprestimo.get() + "," + pago.get();
    }
}
