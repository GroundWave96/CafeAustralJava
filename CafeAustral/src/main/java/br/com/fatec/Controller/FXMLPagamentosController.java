/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.Controller;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXTextField;
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
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author vitor
 */
public class FXMLPagamentosController {

    public class Pagamento {

        private int id;
        private String estadoDoPagamento;
        private String cnpj;
        private double valor;
        private String situacao;

        // Construtor
        public Pagamento(int id, String estadoDoPagamento, String cnpj, double valor, String situacao) {
            this.id = id;
            this.estadoDoPagamento = estadoDoPagamento;
            this.cnpj = cnpj;
            this.valor = valor;
            this.situacao = situacao;
        }

        // Getters
        public int getId() {
            return id;
        }

        public String getEstadoDoPagamento() {
            return estadoDoPagamento;
        }

        public String getCnpj() {
            return cnpj;
        }

        public double getValor() {
            return valor;
        }

        public String getSituacao() {
            return situacao;
        }
    }
    
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
    private TableView<Pagamento> consutlPag_tabelview;
    @FXML
    private TableColumn<Pagamento, Integer> consult_pag_id;
    @FXML
    private TableColumn<Pagamento, String> consult_pag_estado;
    @FXML
    private TableColumn<Pagamento, String> consult_pag_cnpj;
    @FXML
    private TableColumn<Pagamento, Double> consult_pag_val;
    @FXML
    private TableColumn<Pagamento, String> consult_pag_situacao;
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    @FXML
    public void initialize() {
        
        cmb_pag_filtro.getItems().addAll("ID", "Entrega", "Valor", "Situação");
        
        // Configure colunas da tabela
        consult_pag_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        consult_pag_estado.setCellValueFactory(new PropertyValueFactory<>("estadoDoPagamento"));
        consult_pag_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        consult_pag_val.setCellValueFactory(new PropertyValueFactory<>("valor"));
        consult_pag_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        updateTable();

    }

        public String filtro;
    
    public void buscaAvancada() {
        ObservableList<Pagamento> pagList = FXCollections.observableArrayList();

        String query = "";

        connect = DataBase.connectDb();

        
        if (cmb_pag_filtro.getValue() == "ID"){
        query = "SELECT * FROM pagamentos WHERE ID = ? AND CNPJ = ?";
        }else if (cmb_pag_filtro.getValue() == "Entrega"){
        query = "SELECT * FROM pagamentos WHERE EstadoDoPagamento = ? AND CNPJ = ?";
        }else if (cmb_pag_filtro.getValue() == "Valor"){
        query = "SELECT * FROM pagamentos WHERE Valor = ? AND CNPJ = ?";
        }else if (cmb_pag_filtro.getValue() == "Situação"){
        query = "SELECT * FROM pagamentos WHERE Situacao = ? AND CNPJ = ?";
        }
        
        
        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, consult_pag_busca.getText());
            prepare.setString(2, getData.cnpj);
            result = prepare.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String estadoDoPagamento = result.getString("EstadoDoPagamento");
                String cnpj = result.getString("CNPJ");
                double valor = result.getDouble("Valor");
                String situacao = result.getString("Situacao");

                Pagamento pagamento = new Pagamento(id, estadoDoPagamento, cnpj, valor, situacao);
                pagList.add(pagamento);
            }

            consutlPag_tabelview.setItems(pagList);

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
    
    public void updateTable() {
        ObservableList<Pagamento> pagList = FXCollections.observableArrayList();

        String query = "SELECT * FROM pagamentos WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, getData.cnpj);
            result = prepare.executeQuery();

            while (result.next()) {
                int id = result.getInt("ID");
                String estadoDoPagamento = result.getString("EstadoDoPagamento");
                String cnpj = result.getString("CNPJ");
                double valor = result.getDouble("Valor");
                String situacao = result.getString("Situacao");

                Pagamento pagamento = new Pagamento(id, estadoDoPagamento, cnpj, valor, situacao);
                pagList.add(pagamento);
            }

            consutlPag_tabelview.setItems(pagList);

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

    public void menuFornecBack() throws IOException {
        btn_menuFornecBack.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_fornecMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
