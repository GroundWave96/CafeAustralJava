/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package br.com.fatec.DAO;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.Alert;
import br.com.fatec.Controller.model.ProdutoModel;
import br.com.fatec.DATABASE.DataBase;
import java.sql.SQLException;

/**
 *
 * @author gabri
 */
public class CadProdDAO {
    
    
    private Connection connect;
    private PreparedStatement prepare;
    private ResultSet result;
    
    public void cadastrarProduto() {

            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("Preencha todos os campos!");
            alert.showAndWait();
        
            alert.setTitle("Error Message");
            alert.setHeaderText(null);
            alert.setContentText("O separador de decimal deve ser com ponto!");
            alert.showAndWait();

        String insertQuery = "INSERT INTO produtos (CNPJ, NomeDoProduto, Tipo, Descricao, Valor, Quantidade) VALUES (?, ?, ?, ?, ?, ?)";

        try {
            connect = DataBase.connectDb();
            prepare = connect.prepareStatement(insertQuery);
            prepare.setString(1, "");
            prepare.setString(2, "");
            prepare.setString(3, "");
            prepare.setString(4, "");
            prepare.setString(5, "");
            prepare.setString(6, "");

            int rowsInserted = prepare.executeUpdate();
            if (rowsInserted > 0) {
            }

        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
        }
    }
}
