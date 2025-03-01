package com.fundorotativo.fundo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroVoluntarioController {

    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField rgTextField;
    @FXML
    private TextField enderecoTextField;
    @FXML
    private TextField bairroTextField;

    // Método para validar o CPF
    private boolean validarCPF(String cpf) {
        return cpf.length() == 11 && cpf.matches("[0-9]+");
    }

    // Método para verificar se todos os campos estão preenchidos
    private boolean validarCampos(String nome, String cpf, String rg, String endereco, String bairro) {
        return !nome.isEmpty() && !cpf.isEmpty() && !rg.isEmpty() && !endereco.isEmpty() && !bairro.isEmpty();
    }

    // Método para salvar os dados no arquivo de texto
    @FXML
    private void onCadastrarClick(ActionEvent event) {
        String nome = nomeTextField.getText();
        String cpf = cpfTextField.getText();
        String rg = rgTextField.getText();
        String endereco = enderecoTextField.getText();
        String bairro = bairroTextField.getText();

        // Validação de campos obrigatórios
        if (!validarCampos(nome, cpf, rg, endereco, bairro)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos obrigatórios");
            alert.setHeaderText("Preencha todos os campos.");
            alert.showAndWait();
            return;
        }

        // Validação do CPF
        if (!validarCPF(cpf)) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("CPF inválido");
            alert.setHeaderText("O CPF informado é inválido.");
            alert.showAndWait();
            return;
        }

        // Criar e salvar o voluntário no arquivo
        Voluntario voluntario = new Voluntario(nome, cpf, rg, endereco, bairro);
        ArquivoHandler.salvarVoluntario(voluntario);

        // Exibir mensagem de sucesso
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Cadastro realizado");
        alert.setHeaderText("Voluntário cadastrado com sucesso!");
        alert.showAndWait();

        // Limpar os campos
        nomeTextField.clear();
        cpfTextField.clear();
        rgTextField.clear();
        enderecoTextField.clear();
        bairroTextField.clear();
    }

    // Método para voltar à tela anterior
    @FXML
    private void onVoltarButtonClick(ActionEvent event) {
        Stage stage = (Stage) nomeTextField.getScene().getWindow();
        stage.close();
    }
}
