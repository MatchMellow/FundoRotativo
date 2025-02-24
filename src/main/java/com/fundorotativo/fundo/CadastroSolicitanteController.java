package com.fundorotativo.fundo;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CadastroSolicitanteController {

    @FXML
    private TextField nomeTextField;
    @FXML
    private TextField cpfTextField;
    @FXML
    private TextField rgTextField;
    @FXML
    private TextField orgaoExpeditorTextField;

    @FXML
    protected void onCadastrarClick() {
        // Aqui você pode salvar os dados do cadastro
        String nome = nomeTextField.getText();
        String cpf = cpfTextField.getText();
        String rg = rgTextField.getText();
        String orgaoExpeditor = orgaoExpeditorTextField.getText();

        // Salvar ou processar os dados conforme necessário
        System.out.println("Nome: " + nome);
        System.out.println("CPF: " + cpf);
        System.out.println("RG: " + rg);
        System.out.println("Órgão Expeditor: " + orgaoExpeditor);
    }

    @FXML
    protected void onVoltarButtonClick() {
        // Fechar a janela atual e voltar para a tela principal
        Stage stage = (Stage) nomeTextField.getScene().getWindow();
        stage.close();  // Fecha a janela atual
    }
}
