package br.com.fatec.Controller.model;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;

/**
 *
 * @author vitor
 */
public class admMenuModel {
    
    @FXML
    private Button btn_sair;

    @FXML
    private Button btn_cadADM;

    @FXML
    private Button btn_cadForn;

    @FXML
    private Label home_totalAdm;

    @FXML
    private Label home_totalFornecAdm;

    @FXML
    private Label home_supPendente;

    @FXML
    private Label txt_user;
    
    @FXML
    private Button btn_consultProd;
    
    @FXML
    private Button btn_pagamentosADM;
    
    @FXML
    private Button btn_suporteADM;

    @FXML
    public void initializeComponents() {
        // Set default values or data fetched from a source
        btn_sair.setText("Sair");
        btn_cadADM.setText("Cadastrar ADM");
        btn_cadForn.setText("Cadastrar Fornecedor");
        home_totalAdm.setText("Total de ADMs: 0");
        home_totalFornecAdm.setText("Total de Fornecedores: 0");
        home_supPendente.setText("Suportes Pendentes: 0");
        txt_user.setText("Usuário: Admin");
        btn_consultProd.setText("Consultar Produtos");
        btn_pagamentosADM.setText("Pagamentos");
        btn_suporteADM.setText("Suporte");

        // Add action listeners or any other initialization logic here
        btn_sair.setOnAction(event -> handleSair());
        btn_cadADM.setOnAction(event -> handleCadADM());
        btn_cadForn.setOnAction(event -> handleCadForn());
        btn_consultProd.setOnAction(event -> handleConsultProd());
        btn_pagamentosADM.setOnAction(event -> handlePagamentosADM());
        btn_suporteADM.setOnAction(event -> handleSuporteADM());
        
        // Fetch and display actual data from a database or service
        updateLabelsFromDatabase();
    }

    private void handleSair() {
        // Logic to handle "Sair" button click
        System.out.println("Sair button clicked");
    }

    private void handleCadADM() {
        // Logic to handle "Cadastrar ADM" button click
        System.out.println("Cadastrar ADM button clicked");
    }

    private void handleCadForn() {
        // Logic to handle "Cadastrar Fornecedor" button click
        System.out.println("Cadastrar Fornecedor button clicked");
    }

    private void handleConsultProd() {
        // Logic to handle "Consultar Produtos" button click
        System.out.println("Consultar Produtos button clicked");
    }

    private void handlePagamentosADM() {
        // Logic to handle "Pagamentos" button click
        System.out.println("Pagamentos button clicked");
    }

    private void handleSuporteADM() {
        // Logic to handle "Suporte" button click
        System.out.println("Suporte button clicked");
    }

    private void updateLabelsFromDatabase() {
        // Logic to fetch data from the database and update labels
        // Example:
        // home_totalAdm.setText("Total de ADMs: " + fetchTotalADMs());
        // home_totalFornecAdm.setText("Total de Fornecedores: " + fetchTotalFornecedores());
        // home_supPendente.setText("Suportes Pendentes: " + fetchTotalSuportesPendentes());
    }

    // Placeholder methods for database fetch logic
    private int fetchTotalADMs() {
        // Replace with actual database fetch logic
        return 10;
    }

    private int fetchTotalFornecedores() {
        // Replace with actual database fetch logic
        return 5;
    }

    private int fetchTotalSuportesPendentes() {
        // Replace with actual database fetch logic
        return 3;
    }
}
