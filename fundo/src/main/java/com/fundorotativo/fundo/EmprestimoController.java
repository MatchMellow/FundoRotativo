package com.fundorotativo.fundo;

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
    private TextField parcelasTextField;
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

    // Taxa de juros simples (5%)
    private static final double JUROS_PERCENTUAL = 0.05;

    // Método para inicializar os dados na tela
    @FXML
    public void initialize() {
        // Carregar solicitantes e voluntários no ComboBox
        List<Solicitante> solicitantes = ArquivoHandler.lerSolicitantes();
        solicitanteComboBox.getItems().addAll(solicitantes);

        List<Voluntario> voluntarios = ArquivoHandler.lerVoluntarios();
        voluntarioComboBox.getItems().addAll(voluntarios);

        // Adicionando opções de parcelas (1 a 5)
        parcelasComboBox.getItems().addAll(1, 2, 3, 4, 5);
    }

    // Ação ao digitar o valor do empréstimo (calcula juros)
    @FXML
    private void onValorEmprestimoChange() {
        try {
            double valorEmprestimo = Double.parseDouble(valorEmprestimoTextField.getText());
            double juros = valorEmprestimo * JUROS_PERCENTUAL;  // Cálculo dos juros simples
            double valorComJuros = valorEmprestimo + juros;

            // Atualiza os labels com os valores calculados
            valorComJurosLabel.setText("R$ " + String.format("%.2f", valorComJuros));
            valorTotalLabel.setText("R$ " + String.format("%.2f", valorComJuros));

        } catch (NumberFormatException e) {
            valorComJurosLabel.setText("R$ 0,00");
            valorTotalLabel.setText("R$ 0,00");
        }
    }

    // Ação ao clicar no botão "Solicitar Empréstimo"
    @FXML
    private void onSolicitarEmpréstimoClick() {
        // Validar os campos antes de criar o empréstimo
        if (validarCampos()) {
            Solicitante solicitante = solicitanteComboBox.getSelectionModel().getSelectedItem();
            Voluntario voluntario = voluntarioComboBox.getSelectionModel().getSelectedItem();
            double valor = Double.parseDouble(valorEmprestimoTextField.getText());
            int parcelas = parcelasComboBox.getSelectionModel().getSelectedItem();
            LocalDate dataInicial = dataInicialDatePicker.getValue();
            LocalDate dataFinal = dataFinalDatePicker.getValue();
            int diaPagamento = Integer.parseInt(diaPagamentoTextField.getText());

            // Criar um objeto Emprestimo
            Emprestimo emprestimo = new Emprestimo(solicitante, voluntario, valor, parcelas, dataInicial, dataFinal, diaPagamento);

            // Salvar o empréstimo no arquivo
            ArquivoHandler.salvarEmprestimo(emprestimo);

            // Gerar as parcelas e salvar os contratos
            for (int i = 1; i <= parcelas; i++) {
                String parcelaText = "Parcela " + i;
                String dataVencimento = dataInicial.plusMonths(i - 1).toString(); // Incrementa a data de pagamento por mês
                Contrato contrato = new Contrato(solicitante.getNome(), parcelaText, dataVencimento, valor / parcelas, emprestimo.getId());

                // Salvar o contrato no arquivo
                ArquivoHandler.salvarContrato(contrato);
            }

            // Exibir mensagem de sucesso
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Empréstimo Solicitado");
            alert.setHeaderText("Empréstimo solicitado com sucesso!");
            alert.showAndWait();

            // Fechar a janela
            Stage stage = (Stage) valorEmprestimoTextField.getScene().getWindow();
            stage.close();
        }
    }

    // Ação ao clicar no botão "Cancelar"
    @FXML
    private void onCancelarClick() {
        // Fechar a janela sem fazer nada
        Stage stage = (Stage) valorEmprestimoTextField.getScene().getWindow();
        stage.close();
    }

    // Método para validar os campos antes de solicitar o empréstimo
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

    // Método para exibir alertas
    private void showAlert(String title, String message, Alert.AlertType type) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
