/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class FXMLPagamentosController {

    @FXML
    private JFXButton btn_menuFornecBack;

    public void menuFornecBack() throws IOException {
        btn_menuFornecBack.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_fornecMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
