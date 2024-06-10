package br.com.fatec.Controller.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Author: vitor
 */
public class cadastroModel {

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

    @FXML
    public void initializeComponents() {
        // Set default values for the text fields
        txt_CNPJ.setText("");
        txt_senhaCadastro.setText("");
        txt_email.setText("");
        txt_userCadastro.setText("");
        txt_telefone.setText("");

        // Add action listeners to the buttons
        btn_cadastrarNovo.setOnAction(event -> handleCadastrarNovo());

        // Any additional initialization logic here
        lbl_entrarCadastro.setText("Preencha todos os campos para se cadastrar");
    }

    private void handleCadastrarNovo() {
        // Logic to handle "Cadastrar Novo" button click
        System.out.println("Cadastrar Novo button clicked");
        
        // Get the data from the input fields
        String cnpj = txt_CNPJ.getText();
        String senha = txt_senhaCadastro.getText();
        String email = txt_email.getText();
        String username = txt_userCadastro.getText();
        String telefone = txt_telefone.getText();
        
        // Perform the necessary actions with the data
        // For example, save it to a database or validate the input
        if (validateInput(cnpj, senha, email, username, telefone)) {
            saveCadastroData(cnpj, senha, email, username, telefone);
            clearTextFields();
            lbl_entrarCadastro.setText("Cadastro realizado com sucesso!");
        } else {
            System.out.println("Validation failed. Please check the input fields.");
            lbl_entrarCadastro.setText("Erro no cadastro. Verifique os campos.");
        }
    }

    private boolean validateInput(String cnpj, String senha, String email, String username, String telefone) {
        // Implement validation logic here
        // For simplicity, just check that no fields are empty
        return !cnpj.isEmpty() && !senha.isEmpty() && !email.isEmpty() && !username.isEmpty() && !telefone.isEmpty();
    }

    private void saveCadastroData(String cnpj, String senha, String email, String username, String telefone) {
        // Implement the logic to save the Cadastro data
        // This could be a database call or some other form of persistence
        System.out.println("Cadastro Data saved: " + cnpj + ", " + senha + ", " + email + ", " + username + ", " + telefone);
    }

    private void clearTextFields() {
        // Clear the input fields
        txt_CNPJ.clear();
        txt_senhaCadastro.clear();
        txt_email.clear();
        txt_userCadastro.clear();
        txt_telefone.clear();
    }
}
