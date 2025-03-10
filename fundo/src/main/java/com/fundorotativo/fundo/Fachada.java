package com.fundorotativo.fundo;

import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class Fachada {

    private Stage primaryStage;

    public Fachada() {
    }

    public void setPrimaryStage(Stage stage) {
        this.primaryStage = stage;
    }

    // Metodo para carregar a tela de "Pessoa"
    @FXML
    protected void onPessoaButtonClick() {
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fundorotativo/fundo/pessoa-view.fxml"));
            Parent root = fxmlLoader.load();
            Scene scene = new Scene(root, 700.0F, 700.0F);

            if (this.primaryStage == null) {
                this.primaryStage = new Stage();
            }
            this.primaryStage.setScene(scene);
            this.primaryStage.setTitle("Pessoa");
            this.primaryStage.show();
        } catch (IOException e) {
            showAlert("Erro", "Não foi possível carregar a tela de Pessoa.");
            e.printStackTrace();  // Logando a exceção
        }
    }


    // Metodo para carregar a tela de "Empréstimos"
    @FXML
    protected void onEmprestimosButtonClick() throws IOException {
        // Carregar a tela de Empréstimos
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fundorotativo/fundo/emprestimos-view.fxml")); // Caminho Interface
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600.0F, 850.0F);  // Abre uma tela e pode ser ajustada de acordo com a compilação

        if (this.primaryStage == null) {
            this.primaryStage = new Stage();
        }
        this.primaryStage.setScene(scene);
        this.primaryStage.setTitle("Empréstimos");  // Título da janela
        this.primaryStage.show();
    }

    // Método para carregar a tela de "Contratos"
    @FXML
    protected void onContratosButtonClick() throws IOException {
        // Carregar a tela de Contratos
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fundorotativo/fundo/contratos-view.fxml"));  // Caminho da tela de contratos
        Parent root = fxmlLoader.load();  // Carregar a interface de Contratos
        Scene scene = new Scene(root, 600.0F, 400.0F);  // Defina um tamanho adequado para a tela

        if (this.primaryStage == null) {
            this.primaryStage = new Stage();  // Se o primaryStage for null, cria um novo
        }
        this.primaryStage.setScene(scene);  // Define a nova cena no primaryStage
        this.primaryStage.setTitle("Contratos");  // Título da janela
        this.primaryStage.show();  // Mostra o Stage
    }

    // Metodo para carregar a tela de "Relatórios"
    @FXML
    protected final void onRelatoriosButtonClick() throws IOException {
        // Carregar a tela de Relatórios
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fundorotativo/fundo/relatorio-view.fxml"));  // Caminho corrigido para o FXML do Relatório
        Parent root = fxmlLoader.load();
        Scene scene = new Scene(root, 600.0F, 200.0F);  // Ajuste o tamanho conforme necessário

        if (this.primaryStage == null) {
            this.primaryStage = new Stage();  // Se o primaryStage for null, cria um novo
        }
        this.primaryStage.setScene(scene);  // Define a nova cena no primaryStage
        this.primaryStage.setTitle("Relatório do Fundo Solidário");  // Título da janela
        this.primaryStage.show();  // Mostra o Stage
    }

    // Metodo para sair da aplicação
    @FXML
    protected void onSairButtonClick() {
        System.exit(0);  // Encerra a aplicação
    }

    // Método para mostrar alertas na aplicação
    private void showAlert(String title, String message) {
        Alert alert = new Alert(AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();  // Exibe o alerta
    }

    @Override
    public String toString() {
        return "Fachada{" +
                "primaryStage=" + primaryStage +
                '}';
    }
}
