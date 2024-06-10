package br.com.fatec.Controller.model;

import br.com.fatec.Controller.FXMLPagamentosAdmController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * Author: vitor
 */
public class pagamentosAdmModel {
    
    @FXML
    private JFXButton btn_menuAdmBack;

    @FXML
    private JFXTextField txt_pag_val;

    @FXML
    private JFXTextField txt_pag_cnpj;

    @FXML
    private JFXTextField txt_pag_id;

    @FXML
    private JFXComboBox<String> cmb_pag_estado;

    @FXML
    private JFXComboBox<String> cmb_pag_situacao;

    @FXML
    private TableView<FXMLPagamentosAdmController.Pagamento> pag_tableView;
    @FXML
    private TableColumn<FXMLPagamentosAdmController.Pagamento, Integer> pag_col_id;
    @FXML
    private TableColumn<FXMLPagamentosAdmController.Pagamento, String> pag_col_estado;
    @FXML
    private TableColumn<FXMLPagamentosAdmController.Pagamento, String> pag_col_cnpj;
    @FXML
    private TableColumn<FXMLPagamentosAdmController.Pagamento, Double> pag_col_val;
    @FXML
    private TableColumn<FXMLPagamentosAdmController.Pagamento, String> pag_col_situacao;

    @FXML
    public void initializeComponents() {
        // Set default values or configure the components here
        txt_pag_val.setText("");
        txt_pag_cnpj.setText("");
        txt_pag_id.setText("");
        
        cmb_pag_estado.getItems().addAll("Estado1", "Estado2", "Estado3");
        cmb_pag_situacao.getItems().addAll("Situacao1", "Situacao2", "Situacao3");
       
        pag_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pag_col_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        pag_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        pag_col_val.setCellValueFactory(new PropertyValueFactory<>("valor"));
        pag_col_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

    }
}
