package com.fundorotativo.fundo;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloApplication extends Application {

    @Override
    public void start(Stage stage) throws IOException {
        // carregar o arquivo FXML do fundo
        FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("fundo-view.fxml"));

        // abre a janela exe carregado e definir o tamanho
        Scene scene = new Scene(fxmlLoader.load(), 450, 500);

        //passa o estagio das telas para  o controlador
        FundoController fundoController = fxmlLoader.getController();
        fundoController.setPrimaryStage(stage); // Passa o stage para o controlador

        // titulo
        stage.setTitle("Fundo Rotativo Solidário");

        //define o tipo de janela
        stage.setScene(scene);

        // exibição de  janela na tela
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}
