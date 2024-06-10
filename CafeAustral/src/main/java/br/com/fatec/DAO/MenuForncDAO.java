/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.DATABASE.DataBase;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

/**
 *
 * @author gabri
 */
public class MenuForncDAO {
    
    @FXML
    private Button btn_sairFornec;

    @FXML
    private Label home_totalProd;

    @FXML
    private Label home_totalFornec;

    @FXML
    private Label home_pagPendente;

    @FXML
    private Label txt_user;
    
    @FXML
    private Button btn_cadProd;
    
    @FXML
    private Button btn_pagFornec;
    
    @FXML
    private Button btn_supFornec;
    
    

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    public void logout() {
        Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
        alert.setTitle("Confirmation Message");
        alert.setHeaderText(null);
        alert.setContentText("Realmente quer sair?");
        Optional<ButtonType> option = alert.showAndWait();
        try {
            if (option.isPresent() && option.get() == ButtonType.OK) {
                btn_sairFornec.getScene().getWindow().hide();
                Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_login.fxml"));
                Stage stage = new Stage();
                Scene scene = new Scene(root);
                stage.setScene(scene);
                stage.show();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void homeTotalProdutos() {
        String sql = "SELECT COUNT(id) FROM produtos";
        connect = DataBase.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt(1);
            }

            System.out.println("Total Produtos: " + countData); // Depuração
            home_totalProd.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void homeTotalFornecedores() {
        String sql = "SELECT COUNT(id) FROM fornecedores";
        connect = DataBase.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt(1);
            }

            System.out.println("Total Fornecedores: " + countData); // Depuração
            home_totalFornec.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    public void homeTotalPagamentos() {
        String sql = "SELECT COUNT(id) FROM pagamentos";
        connect = DataBase.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt(1);
            }

            System.out.println("Pagamentos Pendentes: " + countData); // Depuração
            home_pagPendente.setText(String.valueOf(countData));

        } catch (Exception e) {
            e.printStackTrace();
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
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
    
    public void cadProdShow () throws IOException {
        btn_cadProd.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_cadastroProduto.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void pagamentosShow () throws IOException {
        btn_pagFornec.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_pagamentos.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void suporteShow () throws IOException {
        btn_supFornec.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_suporte.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    

    public void initialize(URL location, ResourceBundle resources) {
        homeTotalProdutos();
        homeTotalFornecedores();
        homeTotalPagamentos();
    }
}
