package com.fundorotativo.fundo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import java.time.LocalDate;
import java.util.List;

public class EmprestimoController {

    @FXML
    private ComboBox<Solicitante> solicitanteComboBox;
    @FXML
    private ComboBox<Voluntario> voluntarioComboBox;
    @FXML
    private TextField valorEmprestimoTextField;
    @FXML
    private DatePicker dataInicialDatePicker;
    @FXML
    private DatePicker dataFinalDatePicker;
    @FXML
    private TextField diaPagamentoTextField;
    @FXML
    private Label valorComJurosLabel;
    @FXML
    private Label valorTotalLabel;
    @FXML
    private ComboBox<Integer> parcelasComboBox;

    private static final double JUROS_PERCENTUAL = 0.05;

    @FXML
    public void initialize() {
        // Limpa e carrega os valores corretamente
        parcelasComboBox.getItems().clear();
        parcelasComboBox.getItems().addAll(1, 2, 3, 4, 5);

        // Carregar solicitantes e voluntários
        List<Solicitante> solicitantes = ArquivoHandler.lerSolicitantes();
        solicitanteComboBox.getItems().addAll(solicitantes);

        List<Voluntario> voluntarios = ArquivoHandler.lerVoluntarios();
        voluntarioComboBox.getItems().addAll(voluntarios);
    }

    @FXML
    private void onValorEmprestimoChange() {
        try {
            double valorEmprestimo = Double.parseDouble(valorEmprestimoTextField.getText());
            double valorComJuros = valorEmprestimo * (1 + JUROS_PERCENTUAL);

            valorComJurosLabel.setText("R$ " + String.format("%.2f", valorComJuros));
            valorTotalLabel.setText("R$ " + String.format("%.2f", valorComJuros));

        } catch (NumberFormatException e) {
            valorComJurosLabel.setText("R$ 0,00");
            valorTotalLabel.setText("R$ 0,00");
        }
    }

    @FXML
    private void onSolicitarEmprestimoClick() {
        if (validarCampos()) {
            Solicitante solicitante = solicitanteComboBox.getSelectionModel().getSelectedItem();
            Voluntario voluntario = voluntarioComboBox.getSelectionModel().getSelectedItem();
            double valor = Double.parseDouble(valorEmprestimoTextField.getText());
            int parcelas = parcelasComboBox.getSelectionModel().getSelectedItem();
            LocalDate dataInicial = dataInicialDatePicker.getValue();
            LocalDate dataFinal = dataFinalDatePicker.getValue();
            int diaPagamento = Integer.parseInt(diaPagamentoTextField.getText());

            Emprestimo emprestimo = new Emprestimo(solicitante, voluntario, valor, parcelas, dataInicial, dataFinal, diaPagamento);
            ArquivoHandler.salvarEmprestimo(emprestimo);

            for (int i = 1; i <= parcelas; i++) {
                if (!ArquivoHandler.contratoJaExiste(emprestimo.getId(), i)) {
                    String parcelaText = "Parcela " + i;
                    String dataVencimento = dataInicial.plusMonths(i - 1).toString();
                    double valorParcela = valor * Math.pow(1 + JUROS_PERCENTUAL, i) / parcelas;

                    Contrato contrato = new Contrato(solicitante.getNome(), parcelaText, dataVencimento, valorParcela, emprestimo.getId());
                    ArquivoHandler.salvarContrato(contrato);
                }
            }

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empréstimo Solicitado");
            alert.setHeaderText("Empréstimo solicitado com sucesso!");
            alert.showAndWait();

            Stage stage = (Stage) valorEmprestimoTextField.getScene().getWindow();
            stage.close();
        }
    }

    private boolean validarCampos() {
        if (solicitanteComboBox.getSelectionModel().isEmpty() || voluntarioComboBox.getSelectionModel().isEmpty()) {
            showAlert("Campos obrigatórios", "Selecione um solicitante e um voluntário.", Alert.AlertType.WARNING);
            return false;
        }
        if (valorEmprestimoTextField.getText().isEmpty() || parcelasComboBox.getSelectionModel().isEmpty() ||
                dataInicialDatePicker.getValue() == null || dataFinalDatePicker.getValue() == null ||
                diaPagamentoTextField.getText().isEmpty()) {
            showAlert("Campos obrigatórios", "Preencha todos os campos.", Alert.AlertType.WARNING);
            return false;
        }
        return true;
    }

    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }


}
