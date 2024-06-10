/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import br.com.fatec.DATABASE.DataBase;
import com.jfoenix.controls.JFXButton;
import com.jfoenix.controls.JFXComboBox;
import com.jfoenix.controls.JFXTextField;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLIntegrityConstraintViolationException;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

/**
 *
 * @author gabri
 */
public class PagamentosAdmDAO {
    
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
    private TableView<Pagamento> pag_tableView;
    @FXML
    private TableColumn<Pagamento, Integer> pag_col_id;
    @FXML
    private TableColumn<Pagamento, String> pag_col_estado;
    @FXML
    private TableColumn<Pagamento, String> pag_col_cnpj;
    @FXML
    private TableColumn<Pagamento, Double> pag_col_val;
    @FXML
    private TableColumn<Pagamento, String> pag_col_situacao;

    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;

    @FXML
    public void initialize() {
        cmb_pag_estado.getItems().addAll("Pendente", "Completo");
        cmb_pag_situacao.getItems().addAll("Finalizado", "Aguardando");

        // Configure colunas da tabela
        pag_col_id.setCellValueFactory(new PropertyValueFactory<>("id"));
        pag_col_estado.setCellValueFactory(new PropertyValueFactory<>("estadoDoPagamento"));
        pag_col_cnpj.setCellValueFactory(new PropertyValueFactory<>("cnpj"));
        pag_col_val.setCellValueFactory(new PropertyValueFactory<>("valor"));
        pag_col_situacao.setCellValueFactory(new PropertyValueFactory<>("situacao"));

        updateTable();

        pag_tableView.setOnMouseClicked(event -> selectRow());
    }

    @FXML
    public void selectRow() {
        Pagamento selectedPagamento = pag_tableView.getSelectionModel().getSelectedItem();
        if (selectedPagamento != null) {
            txt_pag_id.setText(Integer.toString(selectedPagamento.getId()));
            cmb_pag_estado.setValue(selectedPagamento.getEstadoDoPagamento());
            txt_pag_cnpj.setText(selectedPagamento.getCnpj());
            txt_pag_val.setText(Double.toString(selectedPagamento.getValor()));
            cmb_pag_situacao.setValue(selectedPagamento.getSituacao());
        }
    }

    public void updateTable() {
        clearFields();

        ObservableList<Pagamento> pagList = FXCollections.observableArrayList();

        String query = "SELECT * FROM pagamentos";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
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

            pag_tableView.setItems(pagList);

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

    private void showAlert(Alert.AlertType alertType, String title, String content) {
        Alert alert = new Alert(alertType);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
    
    public void updatePag() {
        String estadoPagamento = cmb_pag_estado.getValue();
        String cnpj = txt_pag_cnpj.getText();
        String valorText = txt_pag_val.getText();
        String situacao = cmb_pag_situacao.getValue();

        // Verifica se algum campo está vazio
        if (estadoPagamento == null || cnpj.isEmpty() || valorText.isEmpty() || situacao == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos antes de cadastrar o pagamento.");
            return;
        }

        // Trata a exceção de conversão para double
        double valor;
        try {
            valor = Double.parseDouble(valorText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "O valor deve ser numérico.");
            return;
        }         
        
               
        String updateQuery = "UPDATE pagamentos SET EstadoDoPagamento = ?, CNPJ = ?, Valor = ?, Situacao = ? WHERE ID = ?";

        try {
            connect = DataBase.connectDb(); // Obtenha a conexão com o banco de dados
            prepare = connect.prepareStatement(updateQuery);

            // Defina os valores dos campos de texto nos parâmetros da consulta
            prepare.setString(1, cmb_pag_estado.getValue());
            prepare.setString(2, txt_pag_cnpj.getText());
            prepare.setDouble(3, Double.parseDouble(txt_pag_val.getText()));
            prepare.setString(4, cmb_pag_situacao.getValue());
            prepare.setInt(5, Integer.parseInt(txt_pag_id.getText()));

            int rowsUpdated = prepare.executeUpdate();
            if (rowsUpdated > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Pagamento atualizado com sucesso!");
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


    public void cadastrarPagamento() {
        String estadoPagamento = cmb_pag_estado.getValue();
        String cnpj = txt_pag_cnpj.getText();
        String valorText = txt_pag_val.getText();
        String situacao = cmb_pag_situacao.getValue();

        // Verifica se algum campo está vazio
        if (estadoPagamento == null || cnpj.isEmpty() || valorText.isEmpty() || situacao == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos antes de cadastrar o pagamento.");
            return;
        }

        // Trata a exceção de conversão para double
        double valor;
        try {
            valor = Double.parseDouble(valorText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "O valor deve ser numérico.");
            return;
        }            
        
        
        String insertQuery = "INSERT INTO pagamentos (EstadoDoPagamento, CNPJ, Valor, Situacao) VALUES (?, ?, ?, ?)";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(insertQuery);

            prepare.setString(1, cmb_pag_estado.getValue());
            prepare.setString(2, txt_pag_cnpj.getText());
            prepare.setDouble(3, Double.parseDouble(txt_pag_val.getText()));
            prepare.setString(4, cmb_pag_situacao.getValue());

            int rowsInserted = prepare.executeUpdate();
            if (rowsInserted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Pagamento cadastrado com sucesso!");
                updateTable();
            }

            } catch (SQLException e) {
                   if (e instanceof SQLIntegrityConstraintViolationException) {
                       showAlert(Alert.AlertType.ERROR, "Erro", "Não foi possível cadastrar o pagamento. Verifique se o CNPJ existe.");
                   } else {
                       e.printStackTrace();
                   }
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

    public void deletarPagamento() {
        
        String estadoPagamento = cmb_pag_estado.getValue();
        String cnpj = txt_pag_cnpj.getText();
        String valorText = txt_pag_val.getText();
        String situacao = cmb_pag_situacao.getValue();

        // Verifica se algum campo está vazio
        if (estadoPagamento == null || cnpj.isEmpty() || valorText.isEmpty() || situacao == null) {
            showAlert(Alert.AlertType.ERROR, "Erro", "Preencha todos os campos antes de cadastrar o pagamento.");
            return;
        }

        // Trata a exceção de conversão para double
        double valor;
        try {
            valor = Double.parseDouble(valorText);
        } catch (NumberFormatException e) {
            showAlert(Alert.AlertType.ERROR, "Erro", "O valor deve ser numérico.");
            return;
        }
        
        String deleteQuery = "DELETE FROM pagamentos WHERE ID = ?";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(deleteQuery);

            prepare.setInt(1, Integer.parseInt(txt_pag_id.getText()));

            int rowsDeleted = prepare.executeUpdate();
            if (rowsDeleted > 0) {
                showAlert(Alert.AlertType.INFORMATION, "Information Message", "Pagamento deletado com sucesso!");
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
    
    private boolean cnpjExistente(String cnpj) {
        String query = "SELECT COUNT(*) FROM fornecedores WHERE ID = ? AND CNPJ = ?";
        int count = 0;

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(query);
            prepare.setInt(1, Integer.parseInt(txt_pag_id.getText()));
            prepare.setString(2, cnpj);
            result = prepare.executeQuery();

            if (result.next()) {
                count = result.getInt(1);
            }

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

        return count > 0;
    }
    
    
    
    public void updateTableCNPJ() {
        pag_tableView.setItems(FXCollections.observableArrayList());
        
        ObservableList<Pagamento> pagList = FXCollections.observableArrayList();

        String query = "SELECT * FROM pagamentos WHERE CNPJ = ?";

        connect = DataBase.connectDb();

        try {
            prepare = connect.prepareStatement(query);
            prepare.setString(1, txt_pag_cnpj.getText());
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

            pag_tableView.setItems(pagList);

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
        txt_pag_id.clear();
        cmb_pag_estado.setValue(null); //NAO FUNCIONA
        txt_pag_cnpj.clear();
        txt_pag_val.clear();
        cmb_pag_situacao.setValue(null); //NAO FUNCIONA
    }

    public void menuAdmBack() throws IOException {
        btn_menuAdmBack.getScene().getWindow().hide();
        Parent root = FXMLLoader.load(getClass().getResource("/br/com/fatec/view/FXML_admMenu.fxml"));
        Stage stage = new Stage();
        Scene scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
