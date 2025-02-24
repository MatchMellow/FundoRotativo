package com.fundorotativo.fundo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PessoaController {

    @FXML
    private Label welcomeText;  // Certifique-se de que o fx:id no FXML é "welcomeText"

    // Ações dos botões
    @FXML
    protected void onCadastrarSolicitanteClick() {
        welcomeText.setText("Cadastrar Solicitante");  // Vai alterar o texto do welcomeText quando clicado
        try {
            // Carregar a tela de cadastro solicitante
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("cadastro-solicitante.fxml"));
            Scene scene = new Scene(fxmlLoader.load(), 400, 400);

            // Pega o Stage atual
            Stage stage = (Stage) welcomeText.getScene().getWindow();

            // Configura a nova cena de cadastro
            stage.setScene(scene);
            stage.setTitle("Cadastrar Solicitante");
            stage.show();
        } catch (IOException e) {
            e.printStackTrace();  // Exibe o erro caso falhe
        }
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
