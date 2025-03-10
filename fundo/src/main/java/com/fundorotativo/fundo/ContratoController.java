package com.fundorotativo.fundo;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import java.util.List;

public class ContratoController {

    @FXML
    private TableView<Contrato> contratoTable;
    @FXML
    private TableColumn<Contrato, String> nomeColumn;
    @FXML
    private TableColumn<Contrato, String> parcelaColumn;
    @FXML
    private TableColumn<Contrato, String> dataColumn;
    @FXML
    private TableColumn<Contrato, Double> valorColumn;
    @FXML
    private Label valorTotalLabel;

    private double valorTotalPago = 0.0;

    @FXML
    private void initialize() {
        // Configuração das colunas da tabela
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        parcelaColumn.setCellValueFactory(new PropertyValueFactory<>("parcela"));
        dataColumn.setCellValueFactory(new PropertyValueFactory<>("data"));
        valorColumn.setCellValueFactory(new PropertyValueFactory<>("valorParcela"));

        // Carregar os contratos sem duplicação
        List<Contrato> contratos = ArquivoHandler.lerContratos();
        contratoTable.getItems().setAll(contratos);

        // Atualizar o valor total inicialmente
        atualizarValorTotal();
    }

    // Método para atualizar o valor total pago
    private void atualizarValorTotal() {
        valorTotalLabel.setText("R$ " + String.format("%.2f", valorTotalPago));
    }

    // Método para marcar a parcela selecionada como paga
    @FXML
    void onMarcarPagoButtonClick() {
        Contrato contratoSelecionado = contratoTable.getSelectionModel().getSelectedItem();

        if (contratoSelecionado != null) {
            // Verifica se já foi pago
            if (contratoSelecionado.isPago()) {
                Alert alert = new Alert(Alert.AlertType.WARNING);
                alert.setTitle("Parcela já paga");
                alert.setHeaderText("Esta parcela já foi marcada como paga.");
                alert.showAndWait();
                return;
            }

            // Recuperar o valor correto da parcela (que já inclui juros no cálculo original)
            double valorParcela = contratoSelecionado.getValorParcela();

            // Debug para verificar se o valor da parcela está correto
            System.out.println("Valor da parcela paga: " + valorParcela);

            // Marcar a parcela como paga
            contratoSelecionado.setStatus("Pago");

            // Atualizar o valor total pago
            valorTotalPago += valorParcela;

            // Atualizar a exibição do valor total
            atualizarValorTotal();

            // Atualizar o contrato no arquivo
            ArquivoHandler.editarContrato(contratoSelecionado);

            // Atualizar o fundo solidário corretamente sem somar juros novamente
            double fundoSolidario = ArquivoHandler.lerFundoSolidario();
            double novoFundo = fundoSolidario + valorParcela;
            System.out.println("Atualizando fundo solidário para: " + novoFundo); // Debug
            ArquivoHandler.atualizarFundoSolidario(novoFundo);

            // Remover parcela do banco de dados
            ArquivoHandler.removerParcela(contratoSelecionado);

            // Atualizar a exibição removendo a parcela paga da tabela
            contratoTable.getItems().remove(contratoSelecionado);

            // Exibir alerta informando que a parcela foi paga
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Parcela Paga");
            alert.setHeaderText("A parcela foi marcada como paga com sucesso!");
            alert.showAndWait();
        } else {
            // Caso nenhum contrato tenha sido selecionado
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção Necessária");
            alert.setHeaderText("Selecione um contrato para marcar como pago.");
            alert.showAndWait();
        }
    }

    // Método para voltar à tela anterior
    @FXML
    private void onVoltarButtonClick() {
        Stage stage = (Stage) contratoTable.getScene().getWindow();
        stage.close();
    }
}
