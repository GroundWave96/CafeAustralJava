package br.com.fatec.Controller.model;

public class ProdutoModel {
    private int id;
    private String cnpj;
    private String nome;
    private String tipo;
    private String descricao;
    private double valor;
    private int quantidade;

    public ProdutoModel(int id, String cnpj, String nome, String tipo, String descricao, double valor, int quantidade) {
        this.id = id;
        this.cnpj = cnpj;
        this.nome = nome;
        this.tipo = tipo;
        this.descricao = descricao;
        this.valor = valor;
        this.quantidade = quantidade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCnpj() {
        return cnpj;
    }

    public void setCnpj(String cnpj) {
        this.cnpj = cnpj;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public double getValor() {
        return valor;
    }

    public void setValor(double valor) {
        this.valor = valor;
    }

    public int getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(int quantidade) {
        this.quantidade = quantidade;
    }
}
