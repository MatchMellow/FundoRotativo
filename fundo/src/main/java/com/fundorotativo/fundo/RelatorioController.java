package com.fundorotativo.fundo;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class RelatorioController {

    @FXML
    private Label fundoSolidarioLabel;  // Label que exibe o valor do fundo solidário

    // Construtor
    public RelatorioController() {
        // Construtor vazio
    }

    // Método para carregar os dados do fundo solidário
    @FXML
    public void initialize() {
        double fundoSolidario = lerFundoSolidario();  // Lê o valor do fundo
        fundoSolidarioLabel.setText("R$ " + String.format("%.2f", fundoSolidario));  // Exibe o valor no Label
    }

    // Método para ler o valor do fundo solidário do arquivo
    private double lerFundoSolidario() {
        double valor = 20000.0;  // Valor inicial do fundo solidário

        try (BufferedReader reader = new BufferedReader(new FileReader("relatorio.txt"))) {
            String linha = reader.readLine();
            if (linha != null && linha.contains("Fundo Solidário:")) {
                valor = Double.parseDouble(linha.split(": ")[1].trim());
            }
        } catch (IOException e) {
            e.printStackTrace();  // Mostra o erro no console
        }

        return valor;
    }

    // Método para atualizar o valor do fundo solidário no arquivo
    public final void atualizarFundoSolidario(double valorPago) {
        double valorAtual = lerFundoSolidario();  // Lê o valor atual do fundo

        // Adiciona o valor pago ao fundo solidário
        double novoValor = valorAtual + valorPago;

        // Salva o novo valor no arquivo
        try (FileWriter writer = new FileWriter("relatorio.txt")) {
            writer.write("Fundo Solidário: " + String.format("%.2f", novoValor));
        } catch (IOException e) {
            e.printStackTrace();  // Mostra o erro no console
        }

        // Atualiza o label com o novo valor
        fundoSolidarioLabel.setText("R$ " + String.format("%.2f", novoValor));
    }

    // Metodo para voltar à tela anterior
    @FXML
    private void onVoltarButtonClick() {
        // Fechar a janela atual (relatório)
        Stage stage = (Stage) fundoSolidarioLabel.getScene().getWindow();
        stage.close();
    }
}
