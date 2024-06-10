package br.com.fatec.Controller.model;

import br.com.fatec.Controller.FXMLConsultaProdutoController;
import com.jfoenix.controls.JFXButton;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * Author: vitor
 */
public class consultaProdutoModel {
    
    @FXML
    private JFXButton btn_menuAdmBack;

    @FXML
    private TableView<FXMLConsultaProdutoController.Produto> consultProd_tableView;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, Integer> seeProd_col_prodID;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, String> seeProd_col_cnpj;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, String> seeProd_col_nomeProd;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, String> seeProd_col_tipoProd;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, String> seeProd_col_descProd;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, Double> seeProd_col_valor;
    @FXML
    private TableColumn<FXMLConsultaProdutoController.Produto, Integer> seeProd_col_qtdProd;

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

    @FXML
    public void initializeComponents() {
        // Initialize the TableView columns
        seeProd_col_prodID.setCellValueFactory(new PropertyValueFactory<>("id"));
        seeProd_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        seeProd_col_nomeProd.setCellValueFactory(new PropertyValueFactory<>("nome"));
        seeProd_col_tipoProd.setCellValueFactory(new PropertyValueFactory<>("tipo"));
        seeProd_col_descProd.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        seeProd_col_valor.setCellValueFactory(new PropertyValueFactory<>("valor"));

    }
}
