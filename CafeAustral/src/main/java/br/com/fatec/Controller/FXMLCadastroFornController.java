/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class FXMLCadastroFornController {

    @FXML
    private JFXButton btn_BackFornAdm;

    @FXML
    private ImageView img_cnpjCadConsult;

    @FXML
    private TextField txt_cnpjCadForn;
    @FXML
    private TextField txt_userCadForn;
    @FXML
    private TextField txt_telCadForn;
    @FXML
    private TextField txt_emailCadForn;
    @FXML
    private TextField txt_senhaCadForn;

    @FXML
    private void buscarFornecedor() {
        buscarFornecedorPorCNPJ();
    }

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void buscarFornecedorPorCNPJ() {
        String cnpj = txt_cnpjCadForn.getText();

        // Verifica se o campo CNPJ não está vazio
        if (cnpj.isEmpty()) {
            limparCampos();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, insira um CNPJ!");
            alert.showAndWait();
            return;
        }

        // Consulta SQL para buscar o fornecedor pelo CNPJ
        String sql = "SELECT Nome, Telefone, Email, Senha FROM fornecedores WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, cnpj);
            result = prepare.executeQuery();

            // Verifica se o CNPJ foi encontrado
            if (result.next()) {
                txt_userCadForn.setText(result.getString("Nome"));
                txt_telCadForn.setText(result.getString("Telefone"));
                txt_emailCadForn.setText(result.getString("Email"));
                txt_senhaCadForn.setText(result.getString("Senha"));
            } else {
                limparCampos();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fornecedor não encontrado com o CNPJ fornecido!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao buscar fornecedor!");
            alert.showAndWait();
        }
    }

// Método auxiliar para limpar os campos de texto
    private void limparCampos() {
        txt_userCadForn.clear();
        txt_telCadForn.clear();
        txt_emailCadForn.clear();
        txt_senhaCadForn.clear();
    }

    public void atualizarFornecedor() {
        String cnpj = txt_cnpjCadForn.getText();

        // Verifica se o campo CNPJ não está vazio
        if (cnpj.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, insira um CNPJ!");
            alert.showAndWait();
            return;
        }

        // Consulta SQL para buscar o fornecedor pelo CNPJ
        String sql = "SELECT * FROM fornecedores WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, cnpj);
            result = prepare.executeQuery();

            // Verifica se o CNPJ foi encontrado
            if (result.next()) {
                // Verifica se todos os campos estão preenchidos
                if (txt_userCadForn.getText().isEmpty() || txt_cnpjCadForn.getText().isEmpty()
                        || txt_telCadForn.getText().isEmpty() || txt_emailCadForn.getText().isEmpty()
                        || txt_senhaCadForn.getText().isEmpty()) {

                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Preencha todos os campos!");
                    alert.showAndWait();
                    return;
                }

                // Verifica se os campos batem com os dados do banco de dados
                String nome = result.getString("Nome");
                String telefone = result.getString("Telefone");
                String email = result.getString("Email");
                String senha = result.getString("Senha");

                boolean hasChanges = false;
                if (!txt_userCadForn.getText().equals(nome)) {
                    hasChanges = true;
                }
                if (!txt_telCadForn.getText().equals(telefone)) {
                    hasChanges = true;
                }
                if (!txt_emailCadForn.getText().equals(email)) {
                    hasChanges = true;
                }
                if (!txt_senhaCadForn.getText().equals(senha)) {
                    hasChanges = true;
                }

                if (!hasChanges) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Não houve alterações no cadastro.");
                    alert.showAndWait();
                } else {
                    // Atualiza o banco de dados com os novos dados
                    String updateSql = "UPDATE fornecedores SET Nome = ?, Telefone = ?, Email = ?, Senha = ? WHERE CNPJ = ?";
                    prepare = connect.prepareStatement(updateSql);
                    prepare.setString(1, txt_userCadForn.getText());
                    prepare.setString(2, txt_telCadForn.getText());
                    prepare.setString(3, txt_emailCadForn.getText());
                    prepare.setString(4, txt_senhaCadForn.getText());
                    prepare.setString(5, cnpj);

                    prepare.executeUpdate();

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Cadastro atualizado com sucesso!");
                    alert.showAndWait();
                }
            } else {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fornecedor com o CNPJ informado não existe!");
                alert.showAndWait();
            }

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao verificar fornecedor!");
            alert.showAndWait();
        }
    }

    @FXML
    private Button btn_delFornAdm;

    @FXML
    private void initialize() {
        btn_delFornAdm.setOnAction(event -> excluirFornecedor());
    }

    public void excluirFornecedor() {
        String cnpj = txt_cnpjCadForn.getText();

        // Verifica se o campo CNPJ não está vazio
        if (cnpj.isEmpty()) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Por favor, insira um CNPJ!");
            alert.showAndWait();
            return;
        }

        // Mostra uma caixa de diálogo de confirmação
        Alert confirmDialog = new Alert(Alert.AlertType.CONFIRMATION);
        confirmDialog.setTitle("Confirmação");
        confirmDialog.setHeaderText(null);
        confirmDialog.setContentText("Tem certeza que deseja excluir o cadastro do fornecedor com CNPJ: " + cnpj + "?");

        Optional<ButtonType> result = confirmDialog.showAndWait();
        if (result.isPresent() && result.get() == ButtonType.OK) {
            // Consulta SQL para excluir o fornecedor pelo CNPJ
            String deleteSql = "DELETE FROM fornecedores WHERE CNPJ = ?";

            connect = DataBase.connectDb();

            try {
                prepare = connect.prepareStatement(deleteSql);
                prepare.setString(1, cnpj);
                int rowsAffected = prepare.executeUpdate();

                if (rowsAffected > 0) {
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setHeaderText(null);
                    alert.setContentText("Fornecedor excluído com sucesso!");
                    alert.showAndWait();

                    // Limpa os campos de texto
                    limparCampos();
                } else {
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Fornecedor com o CNPJ informado não existe!");
                    alert.showAndWait();
                }

            } catch (SQLException e) {
                e.printStackTrace();
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Erro ao excluir fornecedor!");
                alert.showAndWait();
            } finally {
                try {
                    if (prepare != null) {
                        prepare.close();
                    }
                    if (connect != null) {
                        connect.close();
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void cadastrarFornecedor() {
        // Verifica se os campos estão preenchidos
        if (txt_userCadForn.getText().isEmpty() || txt_cnpjCadForn.getText().isEmpty()
                || txt_telCadForn.getText().isEmpty() || txt_emailCadForn.getText().isEmpty()
                || txt_senhaCadForn.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        // Verifica se CNPJ contém apenas números
        if (!txt_cnpjCadForn.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("CNPJ deve conter apenas números!");
            alert.showAndWait();
            return;
        }

        // Verifica se Telefone contém apenas números
        if (!txt_telCadForn.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Telefone deve conter apenas números!");
            alert.showAndWait();
            return;
        }

        // Verifica se Email possui exatamente um "@" e pelo menos um "."
        if (!txt_emailCadForn.getText().contains("@") || !txt_emailCadForn.getText().contains(".")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email deve conter um '@' e um '.'!");
            alert.showAndWait();
            return;
        }

        // Verifica se o CNPJ já está cadastrado
        String cnpj = txt_cnpjCadForn.getText();
        String checkIfExistsSql = "SELECT * FROM fornecedores WHERE CNPJ = ?";
        
        connect = DataBase.connectDb();

        try {
            
            // Verifica se o CNPJ já existe
            String checkCNPJ = "SELECT * FROM fornecedores WHERE CNPJ = ?";
            prepare = connect.prepareStatement(checkCNPJ);
            prepare.setString(1, txt_cnpjCadForn.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("CNPJ já cadastrado!");
                alert.showAndWait();
                return;
            }

            // Verifica se o Email já existe
            String checkEmail = "SELECT * FROM fornecedores WHERE Email = ?";
            prepare = connect.prepareStatement(checkEmail);
            prepare.setString(1, txt_emailCadForn.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Email já cadastrado!");
                alert.showAndWait();
                return;
            }
            
            prepare = connect.prepareStatement(checkIfExistsSql);
            prepare.setString(1, cnpj);
            result = prepare.executeQuery();

            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Fornecedor com o CNPJ informado já está cadastrado!");
                alert.showAndWait();
                return;
            }

            // Inserir dados na tabela fornecedores
            String insertSql = "INSERT INTO fornecedores (Nome, CNPJ, Telefone, Email, Senha) VALUES (?, ?, ?, ?, ?)";
            prepare = connect.prepareStatement(insertSql);
            prepare.setString(1, txt_userCadForn.getText());
            prepare.setString(2, txt_cnpjCadForn.getText());
            prepare.setString(3, txt_telCadForn.getText());
            prepare.setString(4, txt_emailCadForn.getText());
            prepare.setString(5, txt_senhaCadForn.getText());

            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Fornecedor cadastrado com sucesso!");
            alert.showAndWait();

            // Limpar os campos após o cadastro
            txt_userCadForn.clear();
            txt_cnpjCadForn.clear();
            txt_telCadForn.clear();
            txt_emailCadForn.clear();
            txt_senhaCadForn.clear();

        } catch (SQLException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao cadastrar fornecedor!");
            alert.showAndWait();
        } finally {
            try {
                if (result != null) {
                    result.close();
                }
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void forntoAdm() throws IOException {
        btn_BackFornAdm.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_admMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

}
