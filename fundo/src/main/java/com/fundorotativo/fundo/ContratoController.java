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

        // Carregar os contratos
        List<Contrato> contratos = ArquivoHandler.lerContratos();  // Supondo que você tenha essa função para ler contratos

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
    private void onMarcarPagoButtonClick() {
        Contrato contratoSelecionado = contratoTable.getSelectionModel().getSelectedItem();
        if (contratoSelecionado != null) {
            // Marcar a parcela como paga
            contratoSelecionado.setPago(true);

            // Atualizar o valor total pago
            valorTotalPago += contratoSelecionado.getValorParcela();

            // Atualizar o valor total exibido
            atualizarValorTotal();

            // Atualizar o contrato no arquivo
            ArquivoHandler.editarContrato(contratoSelecionado);

            // Exibir um alerta informando que a parcela foi paga
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Parcela Paga");
            alert.setHeaderText("A parcela foi marcada como paga!");
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
        stage.close();  // Fecha a janela atual
    }
}
