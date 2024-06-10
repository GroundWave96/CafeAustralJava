package br.com.fatec.Controller;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * Autor: Vitor
 */
public class FXMLCadastroController {

    @FXML
    private JFXTextField txt_CNPJ;

    @FXML
    private JFXPasswordField txt_senhaCadastro;

    @FXML
    private JFXButton btn_cadastrarNovo;

    @FXML
    private AnchorPane frm_cadastro;

    @FXML
    private Label lbl_entrarCadastro;

    @FXML
    private JFXTextField txt_email;

    @FXML
    private JFXTextField txt_userCadastro;

    @FXML
    private JFXTextField txt_telefone;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void entrar() throws IOException {
        lbl_entrarCadastro.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_login.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cadastrarFornecedor() {
        // Verifica se os campos estão preenchidos
        if (txt_userCadastro.getText().isEmpty() || txt_CNPJ.getText().isEmpty()
                || txt_telefone.getText().isEmpty() || txt_email.getText().isEmpty()
                || txt_senhaCadastro.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        // Verifica se CNPJ contém apenas números
        if (!txt_CNPJ.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("CNPJ deve conter apenas números!");
            alert.showAndWait();
            return;
        }

        // Verifica se Telefone contém apenas números
        if (!txt_telefone.getText().matches("\\d+")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Telefone deve conter apenas números!");
            alert.showAndWait();
            return;
        }

        // Verifica se Email possui exatamente um "@" e pelo menos um "."
        if (!txt_email.getText().contains("@") || !txt_email.getText().contains(".")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Email deve conter um '@' e um '.'!");
            alert.showAndWait();
            return;
        }

        // Conectar ao banco de dados
        connect = DataBase.connectDb();

        try {
            // Verifica se o CNPJ já existe
            String checkCNPJ = "SELECT * FROM fornecedores WHERE CNPJ = ?";
            prepare = connect.prepareStatement(checkCNPJ);
            prepare.setString(1, txt_CNPJ.getText());
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
            prepare.setString(1, txt_email.getText());
            result = prepare.executeQuery();

            if (result.next()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error Message");
                alert.setHeaderText(null);
                alert.setContentText("Email já cadastrado!");
                alert.showAndWait();
                return;
            }

            // Inserir dados na tabela fornecedores
            String sql = "INSERT INTO fornecedores (Nome, CNPJ, Telefone, Email, Senha) VALUES (?, ?, ?, ?, ?)";
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txt_userCadastro.getText());
            prepare.setString(2, txt_CNPJ.getText());
            prepare.setString(3, txt_telefone.getText());
            prepare.setString(4, txt_email.getText());
            prepare.setString(5, txt_senhaCadastro.getText());

            prepare.executeUpdate();

            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Information Message");
            alert.setHeaderText(null);
            alert.setContentText("Fornecedor cadastrado com sucesso!");

            Optional<ButtonType> result = alert.showAndWait();
            if (result.isPresent() && result.get() == ButtonType.OK) {
                // Fechar a janela atual
                Stage currentStage = (Stage) frm_cadastro.getScene().getWindow();
                currentStage.close();

                // Carregar o novo FXML
                Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);

                // Configurar e mostrar a nova janela
                stage.setScene(scene);
                stage.show();
            }

            // Limpar os campos após o cadastro
            txt_userCadastro.clear();
            txt_CNPJ.clear();
            txt_telefone.clear();
            txt_email.clear();
            txt_senhaCadastro.clear();

        } catch (SQLException | IOException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Erro ao cadastrar fornecedor!");
            alert.showAndWait();
        }
    }

}
