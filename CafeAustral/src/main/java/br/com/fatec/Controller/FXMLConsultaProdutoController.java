/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.Controller.model.ProdutoModel;
import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.stage.Stage;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 *
 * @author vitor
 */
public class FXMLConsultaProdutoController {

    public class Produto {

        private int id;
        private String cnpj;
        private String nomeDoProduto;
        private String tipo;
        private String descricao;
        private double valor;
        private int quantidade;

        // Construtor
        public Produto(int id, String cnpj, String nomeDoProduto, String tipo, String descricao, double valor, int quantidade) {
            this.id = id;
            this.cnpj = cnpj;
            this.nomeDoProduto = nomeDoProduto;
            this.tipo = tipo;
            this.descricao = descricao;
            this.valor = valor;
            this.quantidade = quantidade;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getCnpj() {
            return cnpj;
        }

        public String getNomeDoProduto() {
            return nomeDoProduto;
        }

        public String getTipo() {
            return tipo;
        }

        public String getDescricao() {
            return descricao;
        }

        public double getValor() {
            return valor;
        }

        public int getQuantidade() {
            return quantidade;
        }
    }

    @FXML
    private JFXButton btn_menuAdmBack;

    @FXML
    private TableView<Produto> consultProd_tableView;
    @FXML
    private TableColumn<Produto, Integer> seeProd_col_prodID;
    @FXML
    private TableColumn<Produto, String> seeProd_col_cnpj;
    @FXML
    private TableColumn<Produto, String> seeProd_col_nomeProd;
    @FXML
    private TableColumn<Produto, String> seeProd_col_tipoProd;
    @FXML
    private TableColumn<Produto, String> seeProd_col_descProd;
    @FXML
    private TableColumn<Produto, Double> seeProd_col_valor;
    @FXML
    private TableColumn<Produto, Integer> seeProd_col_qtdProd;

    @FXML
    private TextField txt_consult_cnpjProd;
    @FXML
    private TextField txt_consult_nomeProd;
    @FXML
    private TextField txt_consult_tipoProd;
    @FXML
    private TextField txt_consult_descProd;
    @FXML
    private TextField txt_consult_valProd;
    @FXML
    private TextField txt_consult_qtdProd;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public void initialize() {
        updateTable();

        consultProd_tableView.setOnMouseClicked(event -> selectRow());
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
        Produto selectedProduto = consultProd_tableView.getSelectionModel().getSelectedItem();
        if (selectedProduto != null) {
            txt_consult_cnpjProd.setText(selectedProduto.getCnpj());
            txt_consult_nomeProd.setText(selectedProduto.getNomeDoProduto());
            txt_consult_tipoProd.setText(selectedProduto.getTipo());
            txt_consult_descProd.setText(selectedProduto.getDescricao());
            txt_consult_valProd.setText(Double.toString(selectedProduto.getValor()));
            txt_consult_qtdProd.setText(Integer.toString(selectedProduto.getQuantidade()));
        }
    }

    public void updateTable() {
        clearFields();

        ObservableList<Produto> productList = FXCollections.observableArrayList();

        String query = "SELECT * FROM produtos";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            result = prepare.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String cnpj = result.getString("CNPJ");
                String nomeDoProduto = result.getString("NomeDoProduto");
                String tipo = result.getString("Tipo");
                String descricao = result.getString("Descricao");
                double valor = result.getDouble("Valor");
                int quantidade = result.getInt("Quantidade");

                Produto produto = new Produto(id, cnpj, nomeDoProduto, tipo, descricao, valor, quantidade);
                productList.add(produto);
            }

            consultProd_tableView.setItems(productList);

            seeProd_col_prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
            seeProd_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            seeProd_col_nomeProd.setCellValueFactory(new PropertyValueFactory<>("nomeDoProduto"));
            seeProd_col_tipoProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            seeProd_col_descProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            seeProd_col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
            seeProd_col_qtdProd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        } catch (SQLException e) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void updateProduct() {
        
        if (txt_consult_nomeProd.getText().isEmpty() || txt_consult_tipoProd.getText().isEmpty()
                || txt_consult_descProd.getText().isEmpty() || txt_consult_qtdProd.getText().isEmpty()
                || txt_consult_valProd.getText().isEmpty() || txt_consult_cnpjProd.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }        
        
        
        String updateQuery = "UPDATE produtos SET NomeDoProduto = ?, Tipo = ?, Descricao = ?, Valor = ?, Quantidade = ? WHERE CNPJ = ?";

        try {
            connect = DataBase.connectDb(); // Obtenha a conexão com o banco de dados
            prepare = connect.prepareStatement(updateQuery);

            // Defina os valores dos campos de texto nos parâmetros da consulta
            prepare.setString(1, txt_consult_nomeProd.getText());
            prepare.setString(2, txt_consult_tipoProd.getText());
            prepare.setString(3, txt_consult_descProd.getText());
            prepare.setDouble(4, Double.parseDouble(txt_consult_valProd.getText()));
            prepare.setInt(5, Integer.parseInt(txt_consult_qtdProd.getText()));
            prepare.setString(6, txt_consult_cnpjProd.getText());

            int rowsUpdated = prepare.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Produto atualizado com sucesso!");
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

    public void deleteProduct() {
        
            // Verifica se os campos estão preenchidos
        if (txt_consult_nomeProd.getText().isEmpty() || txt_consult_tipoProd.getText().isEmpty()
                || txt_consult_descProd.getText().isEmpty() || txt_consult_qtdProd.getText().isEmpty()
                || txt_consult_valProd.getText().isEmpty() || txt_consult_cnpjProd.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }
    
        
        
        
        String deleteQuery = "DELETE FROM produtos WHERE CNPJ = ?";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(deleteQuery);
            prepare.setString(1, txt_consult_cnpjProd.getText());

            int rowsDeleted = prepare.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Produto deletado com sucesso!");
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
    
    
    public void updateTableCNPJ() {
        consultProd_tableView.setItems(FXCollections.observableArrayList());

        ObservableList<Produto> productList = FXCollections.observableArrayList();

        String query = "SELECT * FROM produtos WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, txt_consult_cnpjProd.getText());
            result = prepare.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String cnpj = result.getString("CNPJ");
                String nomeDoProduto = result.getString("NomeDoProduto");
                String tipo = result.getString("Tipo");
                String descricao = result.getString("Descricao");
                double valor = result.getDouble("Valor");
                int quantidade = result.getInt("Quantidade");

                Produto produto = new Produto(id, cnpj, nomeDoProduto, tipo, descricao, valor, quantidade);
                productList.add(produto);
            }

            consultProd_tableView.setItems(productList);

            seeProd_col_prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
            seeProd_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            seeProd_col_nomeProd.setCellValueFactory(new PropertyValueFactory<>("nomeDoProduto"));
            seeProd_col_tipoProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            seeProd_col_descProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            seeProd_col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
            seeProd_col_qtdProd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        } catch (SQLException e) {
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
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    @FXML
    public void clearFields() {
        txt_consult_cnpjProd.clear();
        txt_consult_nomeProd.clear();
        txt_consult_tipoProd.clear();
        txt_consult_descProd.clear();
        txt_consult_valProd.clear();
        txt_consult_qtdProd.clear();
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
