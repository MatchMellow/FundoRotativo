package com.fundorotativo.fundo;

import java.time.LocalDate;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class Emprestimo {

    private static int idCounter = 1;  // ID único para cada empréstimo
    private int id;
    private Solicitante solicitante;
    private Voluntario voluntario;
    private double valorEmprestimo;
    private int parcelas;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private int diaPagamento;
    private double juros;
    private double valorTotal;
    private double valorParcela;

    // Construtor
    public Emprestimo(Solicitante solicitante, Voluntario voluntario, double valorEmprestimo, int parcelas, LocalDate dataInicial, LocalDate dataFinal, int diaPagamento) {
        this.id = idCounter++;
        this.solicitante = solicitante;
        this.voluntario = voluntario;
        this.valorEmprestimo = valorEmprestimo;
        this.parcelas = parcelas;
        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;
        this.diaPagamento = diaPagamento;
        calcularJuros();
    }

    // Cálculo dos juros distribuídos corretamente entre as parcelas
    private void calcularJuros() {
        double taxaJuros = 0.05;  // 5% de juros simples
        this.juros = valorEmprestimo * taxaJuros;
        this.valorTotal = valorEmprestimo + this.juros;
        this.valorParcela = this.valorTotal / parcelas; // Divide corretamente entre as parcelas
    }


    // Métodos de acesso (getters e setters)
    public int getId() {
        return id;
    }

    public Solicitante getSolicitante() {
        return solicitante;
    }

    public Voluntario getVoluntario() {
        return voluntario;
    }

    public double getValorEmprestimo() {
        return valorEmprestimo;
    }

    public int getParcelas() {
        return parcelas;
    }

    public LocalDate getDataInicial() {
        return dataInicial;
    }

    public LocalDate getDataFinal() {
        return dataFinal;
    }

    public int getDiaPagamento() {
        return diaPagamento;
    }

    public double getJuros() {
        return juros;
    }

    public double getValorTotal() {
        return valorTotal;
    }

    public double getValorParcela() {
        return valorParcela;
    }

    // Método toString para salvar no arquivo
    @Override
    public String toString() {
        return id + "," + solicitante.getNome() + "," + voluntario.getNome() + "," + valorEmprestimo + "," + parcelas + "," + dataInicial + "," + dataFinal + "," + diaPagamento + "," + juros + "," + valorTotal + "," + valorParcela;
    }

    // Método para salvar o empréstimo no arquivo de texto
    public static void salvarEmprestimo(Emprestimo emprestimo) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("emprestimos.txt", true))) {
            writer.write(emprestimo.toString());  // Salva o empréstimo no arquivo
            writer.newLine();  // Adiciona uma nova linha
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // Método para criar um empréstimo a partir de uma linha lida do arquivo
    public static Emprestimo fromString(String line) {
        String[] data = line.split(",");
        Solicitante solicitante = new Solicitante(data[1], "", "", "", "");  // Criar solicitante simplificado
        Voluntario voluntario = new Voluntario(data[2], "", "", "", "");  // Criar voluntário simplificado
        double valorEmprestimo = Double.parseDouble(data[3]);
        int parcelas = Integer.parseInt(data[4]);
        LocalDate dataInicial = LocalDate.parse(data[5]);
        LocalDate dataFinal = LocalDate.parse(data[6]);
        int diaPagamento = Integer.parseInt(data[7]);
        double juros = Double.parseDouble(data[8]);
        double valorTotal = Double.parseDouble(data[9]);
        double valorParcela = Double.parseDouble(data[10]);

        Emprestimo emprestimo = new Emprestimo(solicitante, voluntario, valorEmprestimo, parcelas, dataInicial, dataFinal, diaPagamento);
        emprestimo.juros = juros;
        emprestimo.valorTotal = valorTotal;
        emprestimo.valorParcela = valorParcela;

        return emprestimo;
    }
}
