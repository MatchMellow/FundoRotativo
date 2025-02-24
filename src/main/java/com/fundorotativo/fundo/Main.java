package com.fundorotativo.fundo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class Main extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // carrega o arquivo FXML que foi criado
        FXMLLoader fxmlLoader = new FXMLLoader(Main.class.getResource("fundo-view.fxml"));

        // criar uma janela com o tamanho especificado
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);

        // definir o título do projeto
        stage.setTitle("Fundo Rotativo Solidário");

        // definir a janela
        stage.setScene(scene);

        // mostrar a janela na tela
        stage.show();
    }

    public static void main(String[] args) {
        launch();  // aplicação inicia
    }
}
