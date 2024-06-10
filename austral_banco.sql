create database cafeaustral;

use cafeaustral;

-- Criação das tabelas
CREATE TABLE ADMINISTRADORES (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Genero VARCHAR(20) NOT NULL,
    Telefone VARCHAR(20) NOT NULL,
    Email VARCHAR(50) NOT NULL,
    Senha VARCHAR(50) NOT NULL
);

CREATE TABLE FORNECEDORES (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    Nome VARCHAR(50) NOT NULL,
    Sobrenome VARCHAR(50) NOT NULL,
    CNPJ VARCHAR(20) NOT NULL UNIQUE,
    Email VARCHAR(50) NOT NULL,
    Senha VARCHAR(50) NOT NULL
);

CREATE TABLE PRODUTOS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CNPJ VARCHAR(20) NOT NULL,
    NomeDoProduto VARCHAR(50) NOT NULL,
    Tipo VARCHAR(20) NOT NULL,
    Descricao VARCHAR(50) NOT NULL,
    Valor DOUBLE NOT NULL,
    Quantidade INT NOT NULL,
    FOREIGN KEY (CNPJ) REFERENCES FORNECEDORES(CNPJ)
);

CREATE TABLE PAGAMENTOS (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    EstadoDoPagamento VARCHAR(20) NOT NULL,
    CNPJ VARCHAR(20) NOT NULL,
    Valor DOUBLE NOT NULL,
    Situacao VARCHAR(20) NOT NULL,
    FOREIGN KEY (CNPJ) REFERENCES FORNECEDORES(CNPJ)
);

CREATE TABLE SUPORTE (
    ID INT AUTO_INCREMENT PRIMARY KEY,
    CNPJ VARCHAR(20) NOT NULL,
    Sobre VARCHAR(30) NOT NULL,
    Descricao VARCHAR(500) NOT NULL,
    Situacao VARCHAR(20) NOT NULL,
    FOREIGN KEY (CNPJ) REFERENCES FORNECEDORES(CNPJ)
);

-- Inserção de dados nas tabelas
INSERT INTO ADMINISTRADORES (Nome, Genero, Telefone, Email, Senha) VALUES
('admin', 'Masculino', '1234567890', 'admin1@example.com', 'admin'),
('Admin2', 'Feminino', '2234567890', 'admin2@example.com', 'password2'),
('Admin3', 'Masculino', '3234567890', 'admin3@example.com', 'password3'),
('Admin4', 'Feminino', '4234567890', 'admin4@example.com', 'password4'),
('Admin5', 'Masculino', '5234567890', 'admin5@example.com', 'password5'),
('Admin6', 'Feminino', '6234567890', 'admin6@example.com', 'password6'),
('Admin7', 'Masculino', '7234567890', 'admin7@example.com', 'password7'),
('Admin8', 'Feminino', '8234567890', 'admin8@example.com', 'password8'),
('Admin9', 'Masculino', '9234567890', 'admin9@example.com', 'password9'),
('Admin10', 'Feminino', '1034567890', 'admin10@example.com', 'password10');

INSERT INTO FORNECEDORES (Nome, Sobrenome, CNPJ, Email, Senha) VALUES
('Fornecedor1', 'Sobrenome1', '12345678901234567890', 'fornecedor1@example.com', 'password1'),
('Fornecedor2', 'Sobrenome2', '22345678901234567890', 'fornecedor2@example.com', 'password2'),
('Fornecedor3', 'Sobrenome3', '32345678901234567890', 'fornecedor3@example.com', 'password3'),
('Fornecedor4', 'Sobrenome4', '42345678901234567890', 'fornecedor4@example.com', 'password4'),
('Fornecedor5', 'Sobrenome5', '52345678901234567890', 'fornecedor5@example.com', 'password5'),
('Fornecedor6', 'Sobrenome6', '62345678901234567890', 'fornecedor6@example.com', 'password6'),
('Fornecedor7', 'Sobrenome7', '72345678901234567890', 'fornecedor7@example.com', 'password7'),
('Fornecedor8', 'Sobrenome8', '82345678901234567890', 'fornecedor8@example.com', 'password8'),
('Fornecedor9', 'Sobrenome9', '92345678901234567890', 'fornecedor9@example.com', 'password9'),
('Fornecedor10', 'Sobrenome10', '10345678901234567890', 'fornecedor10@example.com', 'password10');

INSERT INTO PRODUTOS (CNPJ, NomeDoProduto, Tipo, Descricao, Valor, Quantidade) VALUES
('12345678901234567890', 'Produto1', 'Tipo1', 'Descricao1', 10.00, 100),
('22345678901234567890', 'Produto2', 'Tipo2', 'Descricao2', 20.00, 200),
('32345678901234567890', 'Produto3', 'Tipo3', 'Descricao3', 30.00, 300),
('42345678901234567890', 'Produto4', 'Tipo4', 'Descricao4', 40.00, 400),
('52345678901234567890', 'Produto5', 'Tipo5', 'Descricao5', 50.00, 500),
('62345678901234567890', 'Produto6', 'Tipo6', 'Descricao6', 60.00, 600),
('72345678901234567890', 'Produto7', 'Tipo7', 'Descricao7', 70.00, 700),
('82345678901234567890', 'Produto8', 'Tipo8', 'Descricao8', 80.00, 800),
('92345678901234567890', 'Produto9', 'Tipo9', 'Descricao9', 90.00, 900),
('10345678901234567890', 'Produto10', 'Tipo10', 'Descricao10', 100.00, 1000);

INSERT INTO PAGAMENTOS (EstadoDoPagamento, CNPJ, Valor, Situacao) VALUES
('Pendente', '12345678901234567890', 100.00, 'Aguardando'),
('Completo', '22345678901234567890', 200.00, 'Finalizado'),
('Pendente', '32345678901234567890', 300.00, 'Aguardando'),
('Completo', '42345678901234567890', 400.00, 'Finalizado'),
('Pendente', '52345678901234567890', 500.00, 'Aguardando'),
('Completo', '62345678901234567890', 600.00, 'Finalizado'),
('Pendente', '72345678901234567890', 700.00, 'Aguardando'),
('Completo', '82345678901234567890', 800.00, 'Finalizado'),
('Pendente', '92345678901234567890', 900.00, 'Aguardando'),
('Completo', '10345678901234567890', 1000.00, 'Finalizado');

INSERT INTO SUPORTE (CNPJ, Sobre, Descricao, Situacao) VALUES
('12345678901234567890', 'Problema1', 'Descricao detalhada do problema 1', 'Aberto'),
('22345678901234567890', 'Problema2', 'Descricao detalhada do problema 2', 'Fechado'),
('32345678901234567890', 'Problema3', 'Descricao detalhada do problema 3', 'Aberto'),
('42345678901234567890', 'Problema4', 'Descricao detalhada do problema 4', 'Fechado'),
('52345678901234567890', 'Problema5', 'Descricao detalhada do problema 5', 'Aberto'),
('62345678901234567890', 'Problema6', 'Descricao detalhada do problema 6', 'Fechado'),
('72345678901234567890', 'Problema7', 'Descricao detalhada do problema 7', 'Aberto'),
('82345678901234567890', 'Problema8', 'Descricao detalhada do problema 8', 'Fechado'),
('92345678901234567890', 'Problema9', 'Descricao detalhada do problema 9', 'Aberto'),
('10345678901234567890', 'Problema10', 'Descricao detalhada do problema 10', 'Fechado');
