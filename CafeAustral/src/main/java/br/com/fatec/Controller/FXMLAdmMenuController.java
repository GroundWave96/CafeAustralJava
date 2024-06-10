package br.com.fatec.Controller;

import br.com.fatec.DATABASE.DataBase;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Optional;
import java.util.ResourceBundle;

public class FXMLAdmMenuController implements Initializable {

    @FXML
    private Button btn_sair;

    @FXML
    private Button btn_cadADM;

    @FXML
    private Button btn_cadForn;

    @FXML
    private Label home_totalAdm;

    @FXML
    private Label home_totalFornecAdm;

    @FXML
    private Label home_supPendente;

    @FXML
    private Label txt_user;
    
    @FXML
    private Button btn_consultProd;
    
    @FXML
    private Button btn_pagamentosADM;
    
    @FXML
    private Button btn_suporteADM;
    
    
    

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
                btn_sair.getScene().getWindow().hide();
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

    public void cadastroAdm() throws IOException {
        btn_cadForn.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_cadastroAdm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void cadastroForn() throws IOException {
        btn_cadADM.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_cadastroForn.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void consultaProd() throws IOException {
        btn_consultProd.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_consultaProduto.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void pagamentos() throws IOException {
        btn_pagamentosADM.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_pagamentosAdm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    
    public void suporte () throws IOException {
        btn_suporteADM.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_suporteAdm.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    public void homeTotalAdm() {
        String sql = "SELECT COUNT(id) FROM administradores";
        connect = DataBase.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt(1);
            }

            System.out.println("Total Administrators: " + countData); // Depuração
            home_totalAdm.setText(String.valueOf(countData));

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

    public void hometotalFornecAdm() {
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
            home_totalFornecAdm.setText(String.valueOf(countData));

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

    public void homeSuportePendente() {
        String sql = "SELECT COUNT(id) FROM suporte";
        connect = DataBase.connectDb();
        int countData = 0;
        try {
            prepare = connect.prepareStatement(sql);
            result = prepare.executeQuery();

            if (result.next()) {
                countData = result.getInt(1);
            }

            System.out.println("Suporte Pendentes: " + countData); // Depuração
            home_supPendente.setText(String.valueOf(countData));

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
    
    public void displayUsername() {
        txt_user.setText(getData.username);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        displayUsername();
        homeTotalAdm();
        hometotalFornecAdm();
        homeSuportePendente();
    }
}
