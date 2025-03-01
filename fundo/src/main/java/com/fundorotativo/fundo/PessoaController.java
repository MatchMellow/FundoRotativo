package com.fundorotativo.fundo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;

public class PessoaController {

    @FXML
    private Label welcomeText;

    @FXML
    private Button cadastrarSolicitanteButton;
    @FXML
    private Button cadastrarVoluntarioButton;
    @FXML
    private Button visualizarSolicitanteButton;
    @FXML
    private Button visualizarVoluntarioButton;
    @FXML
    private Button voltarButton;

    public PessoaController() {
    }

    // Ação ao clicar em "Cadastrar Solicitante"
    @FXML
    private void onCadastrarSolicitanteClick(ActionEvent event) {
        abrirTela("/com/fundorotativo/fundo/cadastro-solicitante.fxml", "Cadastro de Solicitante");
    }

    // Ação ao clicar em "Cadastrar Voluntário"
    @FXML
    private void onCadastrarVoluntarioClick(ActionEvent event) {
        abrirTela("/com/fundorotativo/fundo/cadastro-voluntario.fxml", "Cadastro de Voluntário");
    }

    // Ação ao clicar em "Visualizar Solicitante"
    @FXML
    private void onVisualizarSolicitanteClick(ActionEvent event) {
        abrirTela("/com/fundorotativo/fundo/visualizar-solicitante.fxml", "Visualizar Solicitante");
    }

    // Ação ao clicar em "Visualizar Voluntário"
    @FXML
    private void onVisualizarVoluntarioClick(ActionEvent event) {
        abrirTela("/com/fundorotativo/fundo/visualizar-voluntario.fxml", "Visualizar Voluntário");
    }

    // Ação ao clicar em "Voltar"
    @FXML
    private void onVoltarButtonClick(ActionEvent event) {
        // Fecha a janela atual (voltar para a tela principal)
        Stage stage = (Stage) voltarButton.getScene().getWindow();
        stage.close();
    }

    // Método genérico para abrir as telas
    private void abrirTela(String caminhoFXML, String titulo) {
        try {
            // Exibe o caminho do FXML para verificação
            System.out.println("Tentando abrir o arquivo FXML: " + caminhoFXML);

            // Carregar o arquivo FXML
            FXMLLoader loader = new FXMLLoader(getClass().getResource(caminhoFXML));
            Stage stage = new Stage();
            stage.setTitle(titulo); // Título da tela
            stage.setScene(new Scene(loader.load())); // Carrega a cena com o conteúdo do FXML
            stage.show(); // Exibe a nova janela

            // Fechar a tela atual
            Stage currentStage = (Stage) cadastrarSolicitanteButton.getScene().getWindow();
            currentStage.close();

        } catch (IOException e) {
            e.printStackTrace();
            // Exibe um alerta em caso de erro ao carregar a tela
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Erro");
            alert.setHeaderText("Erro ao carregar a tela");
            alert.setContentText("Não foi possível abrir a tela: " + titulo + "\nDetalhes do erro: " + e.getMessage());
            alert.showAndWait();
        }
    }

    // Inicializar a mensagem de boas-vindas
    @FXML
    public void initialize() {
        welcomeText.setText("Bem-vindo ao Sistema!"); // Exibe a mensagem de boas-vindas
    }
}
