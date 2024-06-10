package br.com.fatec.Controller.model;

import br.com.fatec.Controller.FXMLSuporteController;
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
public class suporteModel {
    
    @FXML
    private TableView<FXMLSuporteController.Suporte> sup_tableView;
    @FXML
    private TableColumn<FXMLSuporteController.Suporte, Integer> sup_col_id;
    @FXML
    private TableColumn<FXMLSuporteController.Suporte, String> sup_col_cnpj;
    @FXML
    private TableColumn<FXMLSuporteController.Suporte, String> sup_col_sobre;
    @FXML
    private TableColumn<FXMLSuporteController.Suporte, String> sup_col_desc;
    @FXML
    private TableColumn<FXMLSuporteController.Suporte, String> sup_col_situacao;

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

    @FXML
    public void initializeComponents() {
        // Set default values or configure the components here
        txt_sup_cnpj.setText("");
        txt_sup_sobre.setText("");
        txt_sup_id.setText("");
        txt_sup_desc.setText("");

        // Initialize the TableView columns
        sup_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        sup_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        sup_col_sobre.setCellValueFactory(new PropertyValueFactory<>("sobre"));
        sup_col_desc.setCellValueFactory(new PropertyValueFactory<>("descricao"));
        sup_col_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));
    }
}
