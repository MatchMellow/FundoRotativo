package com.fundorotativo.fundo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class VisualizarSolicitanteController {

    @FXML
    private TableView<Solicitante> solicitanteTable;
    @FXML
    private TableColumn<Solicitante, String> nomeColumn;
    @FXML
    private TableColumn<Solicitante, String> cpfColumn;
    @FXML
    private TableColumn<Solicitante, String> rgColumn;
    @FXML
    private TableColumn<Solicitante, String> enderecoColumn;
    @FXML
    private TableColumn<Solicitante, String> bairroColumn;

    // Método para carregar os dados de solicitantes do arquivo
    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        rgColumn.setCellValueFactory(new PropertyValueFactory<>("rg"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        bairroColumn.setCellValueFactory(new PropertyValueFactory<>("bairro"));

        // Carregar os solicitantes do arquivo e mostrar na tabela
        List<Solicitante> solicitantes = ArquivoHandler.lerSolicitantes();
        solicitanteTable.getItems().setAll(solicitantes);
    }

    // Método para editar os dados de um solicitante
    @FXML
    private void onEditarButtonClick(ActionEvent event) {
        Solicitante solicitanteSelecionado = solicitanteTable.getSelectionModel().getSelectedItem();
        if (solicitanteSelecionado != null) {
            // Implementar lógica para editar os dados selecionados
            // Exemplo: Exibir um formulário ou editar diretamente na TableView

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edição");
            alert.setHeaderText("Editando solicitante: " + solicitanteSelecionado.getNome());
            alert.showAndWait();

            // Salvar as alterações no arquivo
            ArquivoHandler.editarSolicitante(solicitanteSelecionado);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção necessária");
            alert.setHeaderText("Selecione um solicitante para editar.");
            alert.showAndWait();
        }
    }

    // Método para excluir um solicitante
    @FXML
    private void onExcluirButtonClick(ActionEvent event) {
        Solicitante solicitanteSelecionado = solicitanteTable.getSelectionModel().getSelectedItem();
        if (solicitanteSelecionado != null) {
            // Excluir o solicitante da lista e atualizar o arquivo
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja excluir o solicitante " + solicitanteSelecionado.getNome() + "?");
            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    // Remover o solicitante da lista e reescrever o arquivo
                    ArquivoHandler.removerSolicitante(solicitanteSelecionado);
                    solicitanteTable.getItems().remove(solicitanteSelecionado);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção necessária");
            alert.setHeaderText("Selecione um solicitante para excluir.");
            alert.showAndWait();
        }
    }

    // Método para voltar à tela anterior
    @FXML
    private void onVoltarButtonClick(ActionEvent event) {
        Stage stage = (Stage) solicitanteTable.getScene().getWindow();
        stage.close();  // Fecha a janela atual (voltar para a tela principal)
    }
}
