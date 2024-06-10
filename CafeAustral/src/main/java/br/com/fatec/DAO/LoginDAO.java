/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.stage.Stage;

/**
 *
 * @author gabri
 */
public class LoginDAO {
    
    @FXML
    private JFXTextField txt_userEntrar;

    @FXML
    private JFXPasswordField txt_senhaEntrar;

    @FXML
    private JFXButton btn_entrar;

    @FXML
    private JFXButton btn_cadastro;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void loginAdmin() {

        String sqlFornecedores = "SELECT * FROM fornecedores WHERE (Email = ? OR CNPJ = ?) AND Senha = ?";
        String sqlAdministradores = "SELECT * FROM administradores WHERE Email = ? AND Senha = ?";

        connect = DataBase.connectDb();

        try {
            // Preparar e executar a consulta para a tabela fornecedores
            prepare = connect.prepareStatement(sqlFornecedores);
            prepare.setString(1, txt_userEntrar.getText());
            prepare.setString(2, txt_userEntrar.getText());
            prepare.setString(3, txt_senhaEntrar.getText());
            result = prepare.executeQuery();

            Alert alert;

            // Verificar se os campos estão vazios
            if (txt_userEntrar.getText().isEmpty() || txt_senhaEntrar.getText().isEmpty()) {
                alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Preencha todos os campos!");
                alert.showAndWait();
            } else {
                // Se a consulta na tabela fornecedores retornar um resultado
                if (result.next()) {

                    String nomeUsuario = result.getString("Nome");

                    
                    String cnpj = result.getString("CNPJ");


                    alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information Message");
                    alert.setHeaderText(null);
                    alert.setContentText("Login bem sucedido!");
                    alert.showAndWait();

                    btn_entrar.getScene().getWindow().hide();
                    Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_fornecMenu.fxml"));
                    Stage stage = new Stage();
                    Scene scene = new Scene(root);

                    stage.setScene(scene);
                    stage.show();
                } else {
                    // Preparar e executar a consulta para a tabela administradores
                    prepare = connect.prepareStatement(sqlAdministradores);
                    prepare.setString(1, txt_userEntrar.getText());
                    prepare.setString(2, txt_senhaEntrar.getText());
                    result = prepare.executeQuery();

                    // Se a consulta na tabela administradores retornar um resultado
                    if (result.next()) {
                        String nomeUsuario = result.getString("Nome");


                        alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Information Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Login bem sucedido!");
                        alert.showAndWait();

                        btn_entrar.getScene().getWindow().hide();
                        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_admMenu.fxml"));
                        Stage stage = new Stage();
                        Scene scene = new Scene(root);

                        stage.setScene(scene);
                        stage.show();

                    } else {
                        alert = new Alert(Alert.AlertType.ERROR);
                        alert.setTitle("Error Message");
                        alert.setHeaderText(null);
                        alert.setContentText("Usuário ou senha incorretos!");
                        alert.showAndWait();
                    }
                }
            }

        } catch (IOException | SQLException e) {
            e.printStackTrace(); // Adicione um rastreamento de pilha para ajudar na depuração
        }
    }

    public void cadastro() throws IOException {
        btn_cadastro.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_cadastro.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);

        stage.setScene(scene);
        stage.show();
    }

    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }

}
