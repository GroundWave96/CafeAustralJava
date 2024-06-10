package br.com.fatec.Controller.model;

import br.com.fatec.Controller.FXMLPagamentosController;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

/**
 * 
 * Author: vitor
 */
public class pagamentosModel {
    
    @FXML
    private JFXButton btn_menuFornecBack;

    @FXML
    private JFXButton btn_menuAdmBack;

    @FXML
    private JFXTextField consult_pag_busca;

    @FXML
    private ComboBox<String> cmb_pag_filtro;

    @FXML
    private JFXButton btn_buscar;

    @FXML
    private TableView<FXMLPagamentosController.Pagamento> consutlPag_tabelview;
    @FXML
    private TableColumn<FXMLPagamentosController.Pagamento, Integer> consult_pag_id;
    @FXML
    private TableColumn<FXMLPagamentosController.Pagamento, String> consult_pag_estado;
    @FXML
    private TableColumn<FXMLPagamentosController.Pagamento, String> consult_pag_cnpj;
    @FXML
    private TableColumn<FXMLPagamentosController.Pagamento, Double> consult_pag_val;
    @FXML
    private TableColumn<FXMLPagamentosController.Pagamento, String> consult_pag_situacao;

    @FXML
    public void initializeComponents() {
        // Set default values or configure the components here
        consult_pag_busca.setText("");
        cmb_pag_filtro.getItems().addAll("Filtro1", "Filtro2", "Filtro3");

        // Initialize the TableView columns
        consult_pag_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        consult_pag_estado.setCellValueFactory(new PropertyValueFactory<>("estado"));
        consult_pag_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        consult_pag_val.setCellValueFactory(new PropertyValueFactory<>("valor"));
        consult_pag_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

    }
}
