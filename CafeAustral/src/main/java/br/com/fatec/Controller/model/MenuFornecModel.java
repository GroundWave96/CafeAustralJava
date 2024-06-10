package br.com.fatec.Controller.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 * 
 * Author: vitor
 */
public class MenuFornecModel {
    
    @FXML
    private Button btn_sairFornec;

    @FXML
    private Label home_totalProd;

    @FXML
    private Label home_totalFornec;

    @FXML
    private Label home_pagPendente;

    @FXML
    private Label txt_user;

    @FXML
    private Button btn_cadProd;

    @FXML
    private Button btn_pagFornec;

    @FXML
    private Button btn_supFornec;

    @FXML
    public void initializeComponents() {
        // Set default values or data to the labels and buttons
        home_totalProd.setText("Total de Produtos: 0");
        home_totalFornec.setText("Total de Fornecedores: 0");
        home_pagPendente.setText("Pagamentos Pendentes: 0");
        txt_user.setText("Usuário: Fornecedor");

        // Add action listeners to the buttons if needed
        btn_sairFornec.setOnAction(event -> handleSairFornec());
        btn_cadProd.setOnAction(event -> handleCadProd());
        btn_pagFornec.setOnAction(event -> handlePagFornec());
        btn_supFornec.setOnAction(event -> handleSupFornec());
    }

    private void handleSairFornec() {
        // Logic to handle "Sair" button click for the Fornecedor
        System.out.println("Sair button clicked for Fornecedor");
        // You can add more logic here if needed
    }

    private void handleCadProd() {
        // Logic to handle "Cadastrar Produto" button click
        System.out.println("Cadastrar Produto button clicked");
        // You can add more logic here if needed
    }

    private void handlePagFornec() {
        // Logic to handle "Pagamentos do Fornecedor" button click
        System.out.println("Pagamentos do Fornecedor button clicked");
        // You can add more logic here if needed
    }

    private void handleSupFornec() {
        // Logic to handle "Suporte do Fornecedor" button click
        System.out.println("Suporte do Fornecedor button clicked");
        // You can add more logic here if needed
    }
}
