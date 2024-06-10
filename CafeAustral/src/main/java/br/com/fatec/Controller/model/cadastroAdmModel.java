package br.com.fatec.Controller.model;

import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXPasswordField;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;

/**
 *
 * @author vitor
 */
public class cadastroAdmModel {

    @FXML
    private JFXButton btn_backAdmForm;

    @FXML
    private JFXComboBox<String> cmb_genCadAdm;

    @FXML
    private JFXTextField txt_userCadAdm;

    @FXML
    private JFXTextField txt_telCadAdm;

    @FXML
    private JFXTextField txt_emailCadAdm;

    @FXML
    private JFXPasswordField txt_senhaCadAdm;

    @FXML
    private JFXButton btn_cadAdm;

    @FXML
    public void initializeComponents() {
        // Initialize the ComboBox with options
        cmb_genCadAdm.getItems().addAll("Masculino", "Feminino", "Outro");

        // Set default values for the text fields
        txt_userCadAdm.setText("");
        txt_telCadAdm.setText("");
        txt_emailCadAdm.setText("");
        txt_senhaCadAdm.setText("");

        // Add action listeners to the buttons
        btn_backAdmForm.setOnAction(event -> handleBackAdmForm());
        btn_cadAdm.setOnAction(event -> handleCadAdm());

        // Any additional initialization logic here
    }

    private void handleBackAdmForm() {
        // Logic to handle "Back" button click
        System.out.println("Back button clicked");
        // Here you might want to navigate back to the previous screen
    }

    private void handleCadAdm() {
        // Logic to handle "Cadastrar ADM" button click
        System.out.println("Cadastrar ADM button clicked");

        // Get the data from the input fields
        String username = txt_userCadAdm.getText();
        String telefone = txt_telCadAdm.getText();
        String email = txt_emailCadAdm.getText();
        String senha = txt_senhaCadAdm.getText();
        String genero = cmb_genCadAdm.getValue();

        // Perform the necessary actions with the data
        // For example, save it to a database or validate the input
        if (validateInput(username, telefone, email, senha, genero)) {
            saveAdmData(username, telefone, email, senha, genero);
            clearTextFields();
        } else {
            System.out.println("Validation failed. Please check the input fields.");
        }
    }

    private boolean validateInput(String username, String telefone, String email, String senha, String genero) {
        // Implement validation logic here
        // For simplicity, just check that no fields are empty
        return !username.isEmpty() && !telefone.isEmpty() && !email.isEmpty() && !senha.isEmpty() && genero != null;
    }

    private void saveAdmData(String username, String telefone, String email, String senha, String genero) {
        // Implement the logic to save the ADM data
        // This could be a database call or some other form of persistence
        System.out.println("ADM Data saved: " + username + ", " + telefone + ", " + email + ", " + senha + ", " + genero);
    }

    private void clearTextFields() {
        // Clear the input fields
        txt_userCadAdm.clear();
        txt_telCadAdm.clear();
        txt_emailCadAdm.clear();
        txt_senhaCadAdm.clear();
        cmb_genCadAdm.getSelectionModel().clearSelection();
    }
}
