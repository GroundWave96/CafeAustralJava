/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import java.io.IOException;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 *
 * @author gabri
 */
public class AdmMenuDAO {
    public void cadastroAdm() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_cadastroAdm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cadastroForn() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_cadastroForn.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void consultaProd() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_consultaProduto.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void pagamentos() throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_pagamentosAdm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void suporte () throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_suporteAdm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
