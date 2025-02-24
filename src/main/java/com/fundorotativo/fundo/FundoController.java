package com.fundorotativo.fundo;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

import java.io.IOException;

public class FundoController {

    // Variável para armazenar o Stage da janela principal
    private Stage primaryStage;

    // Método para definir o Stage
    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    @FXML
    protected void onPessoaButtonClick() throws IOException {
        // Carregar a tela de "Pessoa"
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("pessoa-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 400, 400);

        // Atualizar a cena da janela principal
        primaryStage.setScene(scene);
        primaryStage.setTitle("Pessoa");
        primaryStage.show();
    }

    @FXML
    protected void onEmprestimosButtonClick() {
        showAlert("Empréstimos", "Você clicou em Empréstimos.");
    }

    @FXML
    protected void onParcelasButtonClick() {
        showAlert("Parcelas", "Você clicou em Parcelas.");
    }

    @FXML
    protected void onContratosButtonClick() {
        showAlert("Contratos", "Você clicou em Contratos.");
    }

    @FXML
    protected void onRelatoriosButtonClick() {
        showAlert("Relatórios", "Você clicou em Relatórios.");
    }

    @FXML
    protected void onSairButtonClick() {
        System.exit(0); // Fecha o aplicativo
    }

    private void showAlert(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
