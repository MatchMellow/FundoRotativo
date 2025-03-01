package com.fundorotativo.fundo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        // Carregar o arquivo FXML da tela principal
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/com/fundorotativo/fundo/fachada-view.fxml"));

        // Criar a cena com a tela principal
        Scene scene = new Scene(fxmlLoader.load(), 500, 600);  // Ajuste o tamanho conforme necessário

        // Configurar o título da janela e exibir a cena
        stage.setScene(scene);
        stage.setTitle("Fundo Rotativo Solidário");
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);  // Iniciar a aplicação JavaFX
    }
}
