package com.fundorotativo.fundo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // carrega o arquivo FXML para a tela principal
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fundo-view.fxml"));
        Scene scene = new Scene(fxmlLoader.load(), 600, 500);

        // passa a referência do stage para o controlador que estava sendo uma DIFICUDADE ENORME de transferir telas
        FundoController controller = fxmlLoader.getController();
        controller.setPrimaryStage(stage);

        // definir o título e mostrando a tela
        stage.setTitle("Fundo Rotativo Solidário");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();  // Inicia a aplicação JavaFX
    }
}
