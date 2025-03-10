package com.fundorotativo.fundo;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.util.List;

public class VisualizarVoluntarioController {

    @FXML
    private TableView<Voluntario> voluntarioTable;
    @FXML
    private TableColumn<Voluntario, String> nomeColumn;
    @FXML
    private TableColumn<Voluntario, String> cpfColumn;
    @FXML
    private TableColumn<Voluntario, String> rgColumn;
    @FXML
    private TableColumn<Voluntario, String> enderecoColumn;
    @FXML
    private TableColumn<Voluntario, String> bairroColumn;

    // Método para carregar os dados de voluntários do arquivo
    @FXML
    private void initialize() {
        nomeColumn.setCellValueFactory(new PropertyValueFactory<>("nome"));
        cpfColumn.setCellValueFactory(new PropertyValueFactory<>("cpf"));
        rgColumn.setCellValueFactory(new PropertyValueFactory<>("rg"));
        enderecoColumn.setCellValueFactory(new PropertyValueFactory<>("endereco"));
        bairroColumn.setCellValueFactory(new PropertyValueFactory<>("bairro"));

        // Carregar os voluntários do arquivo e mostrar na tabela
        List<Voluntario> voluntarios = ArquivoHandler.lerVoluntarios();
        voluntarioTable.getItems().setAll(voluntarios);
    }

    // Método para editar os dados de um voluntário
    @FXML
    private void onEditarButtonClick(ActionEvent event) {
        Voluntario voluntarioSelecionado = voluntarioTable.getSelectionModel().getSelectedItem();
        if (voluntarioSelecionado != null) {
            // Exemplo de lógica para editar os dados do voluntário
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edição");
            alert.setHeaderText("Editando voluntário: " + voluntarioSelecionado.getNome());
            alert.showAndWait();

            // Aqui, você pode adicionar um método para editar os dados diretamente na tabela ou em um formulário
            // Salvar as alterações no arquivo
            ArquivoHandler.salvarVoluntario(voluntarioSelecionado);
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção necessária");
            alert.setHeaderText("Selecione um voluntário para editar.");
            alert.showAndWait();
        }
    }

    // Método para excluir um voluntário
    @FXML
    private void onExcluirButtonClick(ActionEvent event) {
        Voluntario voluntarioSelecionado = voluntarioTable.getSelectionModel().getSelectedItem();
        if (voluntarioSelecionado != null) {
            // Excluir o voluntário da lista e atualizar o arquivo
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Confirmação");
            alert.setHeaderText("Deseja excluir o voluntário " + voluntarioSelecionado.getNome() + "?");
            alert.showAndWait().ifPresent(response -> {
                if (response == javafx.scene.control.ButtonType.OK) {
                    // Remover o voluntário da lista e reescrever o arquivo
                    ArquivoHandler.removerVoluntario(voluntarioSelecionado);
                    voluntarioTable.getItems().remove(voluntarioSelecionado);
                }
            });
        } else {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Seleção necessária");
            alert.setHeaderText("Selecione um voluntário para excluir.");
            alert.showAndWait();
        }
    }

    // Método para voltar à tela anterior
    @FXML
    private void onVoltarButtonClick(ActionEvent event) {
        Stage stage = (Stage) voluntarioTable.getScene().getWindow();
        stage.close();
    }
}
