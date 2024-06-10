/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author gabri
 */
public class SuporteAdmDAO {
    
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
    private JFXButton btn_menuAdmBack;

    @FXML
    private TableView<Suporte> sup_tableViewAdm;
    @FXML
    private TableColumn<Suporte, Integer> sup_col_idAdm;
    @FXML
    private TableColumn<Suporte, String> sup_col_cnpjAdm;
    @FXML
    private TableColumn<Suporte, String> sup_col_sobreAdm;
    @FXML
    private TableColumn<Suporte, String> sup_col_descAdm;
    @FXML
    private TableColumn<Suporte, String> sup_col_situacaoAdm;

    @FXML
    private TextField txt_pag_cnpj;
    @FXML
    private ComboBox<String> cmb_pag_situacao;
    @FXML
    private TextField txt_pag_sobre;
    @FXML
    private TextField txt_pag_desc;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public void initialize() {
        cmb_pag_situacao.getItems().addAll("Aberto", "Em andamento", "Fechado");

        updateTable();

        sup_tableViewAdm.setOnMouseClicked(event -> selectRow());
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
        Suporte selectedSuporte = sup_tableViewAdm.getSelectionModel().getSelectedItem();
        if (selectedSuporte != null) {
            txt_pag_cnpj.setText(selectedSuporte.getCnpj());
            txt_pag_sobre.setText(selectedSuporte.getSobre());
            txt_pag_desc.setText(selectedSuporte.getDescricao());
            cmb_pag_situacao.setValue(selectedSuporte.getSituacao());
        }
    }

    public void updateTable() {
        clearFields();

        ObservableList<Suporte> supList = FXCollections.observableArrayList();

        String query = "SELECT * FROM suporte";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
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

            sup_tableViewAdm.setItems(supList);

            sup_col_idAdm.setCellValueFactory(new PropertyValueFactory<>("id"));
            sup_col_cnpjAdm.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            sup_col_sobreAdm.setCellValueFactory(new PropertyValueFactory<>("sobre"));
            sup_col_descAdm.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            sup_col_situacaoAdm.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateSuporte() {
        if (txt_pag_cnpj.getText().isEmpty() || cmb_pag_situacao.getValue().isEmpty()
                || txt_pag_sobre.getText().isEmpty() || txt_pag_desc.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        String updateQuery = "UPDATE suporte SET Sobre = ?, Descricao = ?, Situacao = ? WHERE CNPJ = ?";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(updateQuery);

            prepare.setString(1, txt_pag_sobre.getText());
            prepare.setString(2, txt_pag_desc.getText());
            prepare.setString(3, cmb_pag_situacao.getValue());
            prepare.setString(4, txt_pag_cnpj.getText());

            int rowsUpdated = prepare.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Suporte atualizado com sucesso!");
                updateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void deleteSuporte() {
        if (txt_pag_cnpj.getText().isEmpty() || cmb_pag_situacao.getValue().isEmpty()
                || txt_pag_sobre.getText().isEmpty() || txt_pag_desc.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }

        String deleteQuery = "DELETE FROM suporte WHERE CNPJ = ?";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(deleteQuery);
            prepare.setString(1, txt_pag_cnpj.getText());

            int rowsDeleted = prepare.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Suporte deletado com sucesso!");
                updateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateTableCNPJ() {
        sup_tableViewAdm.setItems(FXCollections.observableArrayList());

        ObservableList<Suporte> supList = FXCollections.observableArrayList();

        String query = "SELECT * FROM suporte WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, txt_pag_cnpj.getText());
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

            sup_tableViewAdm.setItems(supList);

            sup_col_idAdm.setCellValueFactory(new PropertyValueFactory<>("id"));
            sup_col_cnpjAdm.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            sup_col_sobreAdm.setCellValueFactory(new PropertyValueFactory<>("sobre"));
            sup_col_descAdm.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            sup_col_situacaoAdm.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @FXML
    public void clearFields() {
        txt_pag_cnpj.clear();
        txt_pag_sobre.clear();
        txt_pag_desc.clear();
        cmb_pag_situacao.setValue(null);
    }

    @FXML
    private void menuAdmBack() throws IOException {
        btn_menuAdmBack.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_admMenu.fxml"));
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
