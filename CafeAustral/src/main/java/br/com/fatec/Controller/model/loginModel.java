package br.com.fatec.Controller.Model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

public class loginModel {

    @FXML
    private JFXTextField txt_userEntrar;

    @FXML
    private JFXPasswordField txt_senhaEntrar;

    @FXML
    private JFXButton btn_entrar;

    @FXML
    private JFXButton btn_cadastro;

    @FXML
    public void initializeComponents() {
        // Set default values for the text fields
        txt_userEntrar.setText("");
        txt_senhaEntrar.setText("");

        // Add action listeners to the buttons
        btn_entrar.setOnAction(event -> handleEntrar());
        btn_cadastro.setOnAction(event -> handleCadastro());

        // Any additional initialization logic here
    }

    private void handleEntrar() {
        // Logic to handle "Entrar" button click
        String username = txt_userEntrar.getText();
        String password = txt_senhaEntrar.getText();
        
        // Perform login action
    }

    private void handleCadastro() {
        // Logic to handle "Cadastro" button click
        // Navigate to the registration screen or perform registration action
    }
}
