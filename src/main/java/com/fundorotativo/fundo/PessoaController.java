package com.fundorotativo.fundo;

import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

public class PessoaController {

    @FXML
    private Label welcomeText;

    // Ações dos botões
    @FXML
    protected void onCadastrarSolicitanteClick() {
        welcomeText.setText("Cadastrar Solicitante");
        // Aqui você pode abrir uma nova tela ou navegar para outra função
    }

    @FXML
    protected void onCadastrarVoluntarioClick() {
        welcomeText.setText("Cadastrar Voluntário");
    }

    @FXML
    protected void onVisualizarSolicitanteClick() {
        welcomeText.setText("Visualizar Solicitante");
    }

    @FXML
    protected void onVisualizarVoluntarioClick() {
        welcomeText.setText("Visualizar Voluntário");
    }

    @FXML
    protected void onVoltarButtonClick() {
        // Voltar para a tela principal
        Stage stage = (Stage) welcomeText.getScene().getWindow();
        stage.close();  // Fecha a tela atual
    }
}
