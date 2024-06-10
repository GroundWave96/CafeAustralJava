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
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class FXMLCadProdController {

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
    private JFXButton btn_menuFornBack;

    @FXML
    private TextField txt_cad_ID;

    @FXML
    private TextField txt_cad_cnpjProd;

    @FXML
    private TextField txt_cad_nomeProd;

    @FXML
    private TextField txt_cad_tipoProd;

    @FXML
    private TextField txt_cad_descProd;

    @FXML
    private TextField txt_cad_valProd;

    @FXML
    private TextField txt_cad_qtdProd;

    @FXML
    private TableView<Produto> cadProd_tableView;

    @FXML
    private TableColumn<Produto, Integer> addProd_col_prodID;

    @FXML
    private TableColumn<Produto, String> addProd_col_cnpj;

    @FXML
    private TableColumn<Produto, String> addProd_col_nomeProd;

    @FXML
    private TableColumn<Produto, String> addProd_col_tipoProd;

    @FXML
    private TableColumn<Produto, String> addProd_col_descProd;

    @FXML
    private TableColumn<Produto, Double> addProd_col_valor;

    @FXML
    private TableColumn<Produto, Integer> addProd_col_qtdProd;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private ObservableList<ProdutoModel> produtoList;

    @FXML
    public void initialize() {
        txt_cad_cnpjProd.setText(getData.cnpj);  // Preenche automaticamente o campo com o CNPJ do fornecedor logado

        updateTable();

        cadProd_tableView.setOnMouseClicked(event -> selectRow());

        System.out.println(getData.cnpj);

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
        Produto selectedProduto = cadProd_tableView.getSelectionModel().getSelectedItem();
        if (selectedProduto != null) {
            txt_cad_cnpjProd.setText(selectedProduto.getCnpj());
            txt_cad_nomeProd.setText(selectedProduto.getNomeDoProduto());
            txt_cad_tipoProd.setText(selectedProduto.getTipo());
            txt_cad_descProd.setText(selectedProduto.getDescricao());
            txt_cad_valProd.setText(Double.toString(selectedProduto.getValor()));
            txt_cad_qtdProd.setText(Integer.toString(selectedProduto.getQuantidade()));
            txt_cad_ID.setText(Integer.toString(selectedProduto.getId()));
        }
    }

    public void cadastrarProduto() {

        if (txt_cad_nomeProd.getText().isEmpty() || txt_cad_tipoProd.getText().isEmpty()
                || txt_cad_descProd.getText().isEmpty() || txt_cad_valProd.getText().isEmpty()
                || txt_cad_qtdProd.getText().isEmpty()) {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
            return;
        }
        
        if (!txt_cad_valProd.getText().contains(".")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("O separador de decimal deve ser com ponto!");
            alert.showAndWait();
            return;
        }

        String insertQuery = "INSERT INTO produtos (CNPJ, NomeDoProduto, Tipo, Descricao, Valor, Quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1, txt_cad_cnpjProd.getText());
            prepare.setString(2, txt_cad_nomeProd.getText());
            prepare.setString(3, txt_cad_tipoProd.getText());
            prepare.setString(4, txt_cad_descProd.getText());
            prepare.setDouble(5, Double.parseDouble(txt_cad_valProd.getText()));
            prepare.setInt(6, Integer.parseInt(txt_cad_qtdProd.getText()));

            int rowsInserted = prepare.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Produto cadastrado com sucesso!");
                updateTable();
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    public void updateTable() {
                
        cadProd_tableView.setItems(FXCollections.observableArrayList());

        ObservableList<Produto> productList = FXCollections.observableArrayList();

        String query = "SELECT * FROM produtos WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, getData.cnpj);
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

            cadProd_tableView.setItems(productList);

            addProd_col_prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
            addProd_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
            addProd_col_nomeProd.setCellValueFactory(new PropertyValueFactory<>("nomeDoProduto"));
            addProd_col_tipoProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
            addProd_col_descProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
            addProd_col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
            addProd_col_qtdProd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

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
        
        if (!txt_cad_valProd.getText().contains(".")) {
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("O separador de decimal deve ser com ponto!");
            alert.showAndWait();
            return;
        }
        
        String updateQuery = "UPDATE produtos SET NomeDoProduto = ?, Tipo = ?, Descricao = ?, Valor = ?, Quantidade = ? WHERE ID = ?";

        try {
            connect = DataBase.connectDb(); // Obtenha a conexão com o banco de dados
            prepare = connect.prepareStatement(updateQuery);

            // Defina os valores dos campos de texto nos parâmetros da consulta
            prepare.setString(1, txt_cad_nomeProd.getText());
            prepare.setString(2, txt_cad_tipoProd.getText());
            prepare.setString(3, txt_cad_descProd.getText());
            prepare.setDouble(4, Double.parseDouble(txt_cad_valProd.getText()));
            prepare.setInt(5, Integer.parseInt(txt_cad_qtdProd.getText()));
            prepare.setString(6, txt_cad_ID.getText());

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
        String deleteQuery = "DELETE FROM produtos WHERE ID = ?";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(deleteQuery);
            prepare.setString(1, txt_cad_ID.getText());

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

    @FXML
    public void clearFields() {
        txt_cad_nomeProd.clear();
        txt_cad_tipoProd.clear();
        txt_cad_descProd.clear();
        txt_cad_valProd.clear();
        txt_cad_qtdProd.clear();
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

    public void menuFornBack() throws IOException {
        btn_menuFornBack.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_fornecMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
