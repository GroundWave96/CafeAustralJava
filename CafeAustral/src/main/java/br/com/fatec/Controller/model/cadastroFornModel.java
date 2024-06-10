package br.com.fatec.Controller.model;

import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

/**
 * 
 * Author: vitor
 */
public class cadastroFornModel {
    
    @FXML
    private JFXButton btn_BackFornAdm;

    @FXML
    private ImageView img_cnpjCadConsult;

    @FXML
    private AnchorPane frm_cadastroadm;

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
    public void initializeComponents() {
        // Set default values for the text fields
        txt_cnpjCadForn.setText("");
        txt_userCadForn.setText("");
        txt_telCadForn.setText("");
        txt_emailCadForn.setText("");
        txt_senhaCadForn.setText("");

        // Add action listeners to the buttons
        btn_BackFornAdm.setOnAction(event -> handleBackFornAdm());

        // Any additional initialization logic here
    }

    private void handleBackFornAdm() {
        // Logic to handle "Back" button click
        System.out.println("Back button clicked");
        // Here you might want to navigate back to the previous screen
    }
}
