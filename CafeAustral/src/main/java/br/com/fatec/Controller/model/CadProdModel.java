package br.com.fatec.Controller.model;

import br.com.fatec.Controller.FXMLCadProdController;
import com.jfoenix.controls.JFXButton;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

public class CadProdModel {

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
    private TableView<FXMLCadProdController.Produto> cadProd_tableView;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, Integer> addProd_col_prodID;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, String> addProd_col_cnpj;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, String> addProd_col_nomeProd;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, String> addProd_col_tipoProd;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, String> addProd_col_descProd;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, Double> addProd_col_valor;

    @FXML
    private TableColumn<FXMLCadProdController.Produto, Integer> addProd_col_qtdProd;

    private ObservableList<FXMLCadProdController.Produto> produtoList;

    public void initialize() {
        // Initialize the TableView columns
        addProd_col_prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        addProd_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        addProd_col_nomeProd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        addProd_col_tipoProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        addProd_col_descProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        addProd_col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));
        addProd_col_qtdProd.setCellValueFactory(new PropertyValueFactory<>("quantidade"));

        // Initialize the product list
        produtoList = FXCollections.observableArrayList();
        cadProd_tableView.setItems(produtoList);
    }

    public void addProduto() {
        // Get the data from the text fields
        int id = Integer.parseInt(txt_cad_ID.getText());
        String cnpj = txt_cad_cnpjProd.getText();
        String nome = txt_cad_nomeProd.getText();
        String tipo = txt_cad_tipoProd.getText();
        String descricao = txt_cad_descProd.getText();
        double valor = Double.parseDouble(txt_cad_valProd.getText());
        int quantidade = Integer.parseInt(txt_cad_qtdProd.getText());

        // Clear the text fields
        clearTextFields();
    }

    private void clearTextFields() {
        txt_cad_ID.clear();
        txt_cad_cnpjProd.clear();
        txt_cad_nomeProd.clear();
        txt_cad_tipoProd.clear();
        txt_cad_descProd.clear();
        txt_cad_valProd.clear();
        txt_cad_qtdProd.clear();
    }
}
