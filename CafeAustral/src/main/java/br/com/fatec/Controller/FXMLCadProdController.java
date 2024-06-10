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

    @FXML
    private JFXButton btn_menuFornBack;

    @FXML
    private ImageView img_cadProdBuscCnpj;

    @FXML
    private TextField txt_cad_cnpjProd;

    @FXML
    private TextField txt_cad_nomeProd;

    @FXML
    private TextField txt_cad_tipoProd;

    @FXML
    private TextField txt_cad_descProd;

    @FXML
    private TextField txt_cad_valorProd;

    @FXML
    private TextField txt_cad_qtdProd;

    @FXML
    private TableView<ProdutoModel> cadProd_tableView;

    @FXML
    private TableColumn<ProdutoModel, Integer> addProd_col_prodID;

    @FXML
    private TableColumn<ProdutoModel, String> addProd_col_cnpj;

    @FXML
    private TableColumn<ProdutoModel, String> addProd_col_nomeProd;

    @FXML
    private TableColumn<ProdutoModel, String> addProd_col_tipoProd;

    @FXML
    private TableColumn<ProdutoModel, String> addProd_col_descProd;

    @FXML
    private TableColumn<ProdutoModel, Double> addProd_col_valor;

    @FXML
    private TableColumn<ProdutoModel, Integer> addProd_col_qtdProd;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    private ObservableList<ProdutoModel> produtoList;

    @FXML
    public void initialize() {
        img_cadProdBuscCnpj.setOnMouseClicked(event -> buscarCnpj());
        txt_cad_cnpjProd.setText(getData.cnpj);  // Preenche automaticamente o campo com o CNPJ do fornecedor logado

        initTable();
        loadTableData();
    }

    @FXML
    private void buscarCnpj() {
        txt_cad_cnpjProd.setText(getData.cnpj);
    }

    @FXML
    private void cadastrarProduto() {
        String sql = "INSERT INTO produtos (CNPJ, NomeDoProduto, Tipo, Descricao, Valor, Quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txt_cad_cnpjProd.getText());
            prepare.setString(2, txt_cad_nomeProd.getText());
            prepare.setString(3, txt_cad_tipoProd.getText());
            prepare.setString(4, txt_cad_descProd.getText());
            prepare.setString(5, txt_cad_valorProd.getText());
            prepare.setString(6, txt_cad_qtdProd.getText());

            if (txt_cad_cnpjProd.getText().isEmpty() || txt_cad_nomeProd.getText().isEmpty()
                    || txt_cad_tipoProd.getText().isEmpty() || txt_cad_descProd.getText().isEmpty()
                    || txt_cad_valorProd.getText().isEmpty() || txt_cad_qtdProd.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Por favor, preencha todos os campos!");
            } else {
                prepare.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Produto cadastrado com sucesso!");
                clearFields();
                loadTableData(); // Atualiza a tabela após cadastrar o produto
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @FXML
    private void editarProduto() {
        String sql = "UPDATE produtos SET NomeDoProduto = ?, Tipo = ?, Descricao = ?, Valor = ?, Quantidade = ? WHERE CNPJ = ? AND NomeDoProduto = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txt_cad_nomeProd.getText());
            prepare.setString(2, txt_cad_tipoProd.getText());
            prepare.setString(3, txt_cad_descProd.getText());
            prepare.setString(4, txt_cad_valorProd.getText());
            prepare.setString(5, txt_cad_qtdProd.getText());
            prepare.setString(6, txt_cad_cnpjProd.getText());
            prepare.setString(7, txt_cad_nomeProd.getText());

            if (txt_cad_nomeProd.getText().isEmpty() || txt_cad_tipoProd.getText().isEmpty()
                    || txt_cad_descProd.getText().isEmpty() || txt_cad_valorProd.getText().isEmpty()
                    || txt_cad_qtdProd.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Por favor, preencha todos os campos!");
            } else {
                prepare.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Produto atualizado com sucesso!");
                clearFields();
                loadTableData(); // Atualiza a tabela após editar o produto
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @FXML
    private void deletarProduto() {
        String sql = "DELETE FROM produtos WHERE CNPJ = ? AND NomeDoProduto = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, txt_cad_cnpjProd.getText());
            prepare.setString(2, txt_cad_nomeProd.getText());

            if (txt_cad_nomeProd.getText().isEmpty()) {
                showAlert(Alert.AlertType.ERROR, "Error Message", "Por favor, insira o nome do produto!");
            } else {
                prepare.executeUpdate();
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Produto deletado com sucesso!");
                clearFields();
                loadTableData(); // Atualiza a tabela após deletar o produto
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

    @FXML
    private void limparCampos() {
        clearFields();
    }

    private void clearFields() {
        txt_cad_cnpjProd.clear();
        txt_cad_nomeProd.clear();
        txt_cad_tipoProd.clear();
        txt_cad_descProd.clear();
        txt_cad_valorProd.clear();
        txt_cad_qtdProd.clear();
    }

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
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

    private void initTable() {
        addProd_col_prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProd_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        addProd_col_nomeProd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        addProd_col_tipoProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        addProd_col_descProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        addProd_col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        addProd_col_qtdProd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        produtoList = FXCollections.observableArrayList();
        cadProd_tableView.setItems(produtoList);
    }

    private void loadTableData() {
        produtoList.clear();
        String sql = "SELECT * FROM produtos WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(sql);
            prepare.setString(1, getData.cnpj);
            result = prepare.executeQuery();

            while (result.next()) {
                ProdutoModel produto = new ProdutoModel(
                        result.getInt("ID"),
                        result.getString("CNPJ"),
                        result.getString("NomeDoProduto"),
                        result.getString("Tipo"),
                        result.getString("Descricao"),
                        result.getDouble("Valor"),
                        result.getInt("Quantidade")
                );
                produtoList.add(produto);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            closeConnection();
        }
    }

}
