/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Controller;

import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import br.com.fatec.DATABASE.DataBase;
import java.sql.SQLException;
import javafx.scene.control.Alert;

import javafx.fxml.FXML;

import javafx.scene.control.TableColumn;

/**
 *
 * @author vitor
 */
public class FXMLSuporteController {

    public class Suporte {

        private int id;
        private String cnpj;
        private String sobre;
        private String descricao;
        private String situacao;

        // Construtor
        public Suporte(int id, String cnpj, String sobre, String descricao, String situacao) {
            this.id = id;
            this.cnpj = cnpj;
            this.sobre = sobre;
            this.descricao = descricao;
            this.situacao = situacao;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getCnpj() {
            return cnpj;
        }

        public String getSobre() {
            return sobre;
        }

        public String getDescricao() {
            return descricao;
        }

        public String getSituacao() {
            return situacao;
        }

    }

    @FXML
    private TableView<Suporte> sup_tableView;
    @FXML
    private TableColumn<Suporte, Integer> sup_col_id;
    @FXML
    private TableColumn<Suporte, String> sup_col_cnpj;
    @FXML
    private TableColumn<Suporte, String> sup_col_sobre;
    @FXML
    private TableColumn<Suporte, String> sup_col_desc;
    @FXML
    private TableColumn<Suporte, String> sup_col_situacao;

    @FXML
    private TextField txt_sup_cnpj;

    @FXML
    private TextField txt_sup_sobre;

    @FXML
    private TextField txt_sup_id;

    @FXML
    private TextField txt_sup_desc;

    @FXML
    private JFXButton btn_menuFornBack;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public void initialize() {
        updateTable();
        txt_sup_cnpj.setText(getData.cnpj);
        sup_tableView.setOnMouseClicked(event -> selectRow());
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    @FXML
    public void selectRow() {
        Suporte selectedSuporte = sup_tableView.getSelectionModel().getSelectedItem();
        if (selectedSuporte != null) {
            txt_sup_cnpj.setText(selectedSuporte.getCnpj());
            txt_sup_sobre.setText(selectedSuporte.getSobre());
            txt_sup_desc.setText(selectedSuporte.getDescricao());
            txt_sup_id.setText(Integer.toString(selectedSuporte.getId()));

        }
    }

    public void updateTable() {
        clearFields();

        ObservableList<Suporte> supList = FXCollections.observableArrayList();

        String query = "SELECT * FROM suporte WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, getData.cnpj);
            result = prepare.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String cnpj = result.getString("CNPJ");
                String sobre = result.getString("Sobre");
                String descricao = result.getString("Descricao");
                String situacao = result.getString("Situacao");

                Suporte suporte = new Suporte(id, cnpj, sobre, descricao, situacao);
                supList.add(suporte);
            }

            sup_tableView.setItems(supList);

            sup_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
            sup_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            sup_col_sobre.setCellValueFactory(new PropertyValueFactory<>("sobre"));
            sup_col_desc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            sup_col_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void cadastrarSuporte() {

        if (txt_sup_sobre.getText().isEmpty()
                || txt_sup_desc.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        String insertQuery = "INSERT INTO suporte (CNPJ, Sobre, Descricao, Situacao) VALUES (?, ?, ?, ?)";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1, txt_sup_cnpj.getText());
            prepare.setString(2, txt_sup_sobre.getText());
            prepare.setString(3, txt_sup_desc.getText());
            prepare.setString(4, "Aberto");

            int rowsInserted = prepare.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Suporte cadastrado com sucesso!");
                updateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteSuporte() {

        if (txt_sup_sobre.getText().isEmpty()
                || txt_sup_desc.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        String deleteQuery = "DELETE FROM suporte WHERE ID = ?";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(deleteQuery);
            prepare.setString(1, txt_sup_id.getText());

            int rowsDeleted = prepare.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Suporte deletado com sucesso!");
                updateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateSuporte() {

        if (txt_sup_sobre.getText().isEmpty()
                || txt_sup_desc.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        String updateQuery = "UPDATE suporte SET Sobre = ?, Descricao = ? WHERE ID = ?";

        try {
            connect = DataBase.connectDb(); // Obtenha a conexão com o banco de dados
            prepare = connect.prepareStatement(updateQuery);

            // Defina os valores dos campos de texto nos parâmetros da consulta
            prepare.setString(1, txt_sup_sobre.getText());
            prepare.setString(2, txt_sup_desc.getText());
            prepare.setString(3, txt_sup_id.getText());

            int rowsUpdated = prepare.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Suporte atualizado com sucesso!");
                updateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (prepare != null) {
                    prepare.close();
                }
                if (connect != null) {
                    connect.close();
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clearFields() {
        txt_sup_sobre.clear();
        txt_sup_desc.clear();
        txt_sup_id.clear();
    }

    public void menuFornBack() throws IOException {
        btn_menuFornBack.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_fornecMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

    private void closeConnection() {
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
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
