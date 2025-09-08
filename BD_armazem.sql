CREATE DATABASE BD_Armazem_Agricola;

USE BD_Armazem_Agricola;

CREATE TABLE Item(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 Nome VARCHAR(80) NOT NULL,
 Quantidade INT NOT NULL,
 Data_entrada DATE NOT NULL,
 Data_validade DATE NOT NULL,
 Lote VARCHAR(50) NOT NULL,
 Ponto_ressuprimento INT NOT NULL
);

CREATE TABLE Funcionario(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 Nome VARCHAR(50) NOT NULL,
 Sobrenome VARCHAR(50) NOT NULL,
 cargo VARCHAR(50) NOT NULL,
 email VARCHAR(100) NOT NULL
);

CREATE TABLE Usuario(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 Nome_usuario VARCHAR(50) NOT NULL UNIQUE,
 Senha_hashed VARCHAR(255) NOT NULL,
 Area_acesso VARCHAR(50) NOT NULL,
 Funcionario_id INT NOT NULL,
 FOREIGN KEY(Funcionario_id) REFERENCES Funcionario(id)
);

CREATE TABLE Papeis(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 Nome VARCHAR(50) NOT NULL,
 Descricao VARCHAR(255) NOT NULL
);

CREATE TABLE Usuario_Papel(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 usuario_id INT NOT NULL,
 papel_id INT NOT NULL,
 FOREIGN KEY(usuario_id) REFERENCES Usuario(id),
 FOREIGN KEY(papel_id) REFERENCES Papeis(id),
 UNIQUE(usuario_id,papel_id)
);

CREATE TABLE Alerta(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 Tipo_alerta VARCHAR(50) NOT NULL,
 Data_hora DATETIME NOT NULL,
 item_id INT NOT NULL,
 FOREIGN KEY(item_id) REFERENCES Item(ID)
);

CREATE TABLE Transacao(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 data DATETIME NOT NULL,
 tipo VARCHAR(10) NOT NULL,
 funcionario_id INT NOT NULL,
 FOREIGN KEY(funcionario_id) REFERENCES Funcionario(id)
);

CREATE TABLE Transacao_item (
 id INT AUTO_INCREMENT PRIMARY KEY,
 transacao_id INT NOT NULL,
 item_id INT NOT NULL,
 Quantidade INT NOT NULL,
 FOREIGN KEY (item_id) REFERENCES item(id),
 FOREIGN KEY (transacao_id) REFERENCES transacao(id),
 UNIQUE (transacao_id, item_id)
);

CREATE TABLE Telefone_Funcionario(
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 telefone_celular VARCHAR(20) NOT NULL,
 funcionario_id INT NOT NULL,
 FOREIGN KEY(funcionario_id) REFERENCES Funcionario(id)
);

CREATE TABLE Relatorio( 
 id INT AUTO_INCREMENT PRIMARY KEY NOT NULL,
 total_transacoes INT NOT NULL,
 total_saidas INT NOT NULL,
 total_entradas INT NOT NULL,
 data_geracao DATETIME NOT NULL,
 item_id INT NOT NULL,
 FOREIGN KEY(item_id) REFERENCES Item(id)
);

-- Povoando as tabelas

INSERT INTO Funcionario(Nome, Sobrenome, cargo, email)VALUES
('João', 'Silva', 'Gerente', 'joao.silva@example.com'),
('Maria', 'Fernandes', 'Equipe de Vendas', 'maria.fernandes@example.com'),
('Carlos', 'Santos', 'Equipe de Compras', 'carlos.santos@example.com'),
('Ana', 'Oliveira', 'Armazenista', 'ana.oliveira@example.com');

INSERT INTO Telefone_Funcionario(telefone_celular, funcionario_id)VALUES
('11999999999', 1),
('11988888888', 2),
('11977777777', 3),
('11966666666', 4);

INSERT INTO Item(Nome,Quantidade,Data_entrada, Data_validade, Lote, Ponto_ressuprimento) VALUES
('Arroz', 100, '2024-01-01', '2025-01-01', 'L123', 20),
('Feijão', 200, '2024-02-01', '2025-02-01', 'L124', 30),
('Açúcar', 150, '2024-03-01', '2025-03-01', 'L125', 25),
('Sal', 120, '2024-04-01', '2025-04-01', 'L126', 15);

INSERT INTO Alerta(Tipo_alerta, Data_hora, item_id)VALUES
('Ponto de Ressuprimento', '2024-06-19 10:00:00', 1),
('Validade Próxima', '2024-06-20 11:00:00', 2);

-- Inserindo transações para teste
INSERT INTO Transacao(data, tipo, funcionario_id) VALUES
('2024-05-01 10:00:00', 'Entrada', 1),
('2024-05-02 11:00:00', 'Saída', 2),
('2024-05-03 12:00:00', 'Entrada', 3),
('2024-05-04 13:00:00', 'Saída', 4);

INSERT INTO Transacao_item(transacao_id, item_id, Quantidade) VALUES
(1, 1, 50), -- Entrada de 50 unidades de Arroz
(2, 2, 30), -- Saída de 30 unidades de Feijão
(3, 3, 20), -- Entrada de 20 unidades de Açúcar
(4, 4, 10); -- Saída de 10 unidades de Sal


-- Criação de papéis e permissões no banco de dados
CREATE ROLE 'Gerente';
CREATE ROLE 'Vendas';
CREATE ROLE 'Compras';
CREATE ROLE 'Armazem';

-- Concessão de permissões para os papéis
GRANT SELECT, INSERT, UPDATE, DELETE ON BD_Armazem_Agricola.* TO 'Gerente';
GRANT SELECT ON BD_Armazem_Agricola.* TO 'Vendas';
GRANT SELECT, INSERT ON BD_Armazem_Agricola.* TO 'Compras';
GRANT SELECT, INSERT, UPDATE ON BD_Armazem_Agricola.* TO 'Armazem';

-- Criação de usuários do banco de dados e atribuição de papéis
CREATE USER 'joaos'@'localhost' IDENTIFIED BY 'senha123';
GRANT 'Gerente' TO 'joaos'@'localhost';

CREATE USER 'mariaf'@'localhost' IDENTIFIED BY 'senha123';
GRANT 'Vendas' TO 'mariaf'@'localhost';

CREATE USER 'carloss'@'localhost' IDENTIFIED BY 'senha123';
GRANT 'Compras' TO 'carloss'@'localhost';

CREATE USER 'anao'@'localhost' IDENTIFIED BY 'senha123';
GRANT 'Armazem' TO 'anao'@'localhost';

-- Aplicação das permissões
FLUSH PRIVILEGES;

-- VIEWS 

CREATE VIEW VW_Estoque_Baixo AS
SELECT *
FROM Item
WHERE Quantidade <= Ponto_ressuprimento;

CREATE VIEW VW_Alerta_Validade AS
SELECT *
FROM Alerta
WHERE Tipo_alerta = 'Validade Próxima';

CREATE VIEW VW_Relatorio_Transacoes AS
SELECT *
FROM Transacao;

SELECT * FROM VW_Estoque_Baixo;
SELECT * FROM VW_Alerta_Validade;
SELECT * FROM VW_Relatorio_Transacoes;

DELIMITER //

-- Procedure para calcular o total de entradas e saídas de um item específico
CREATE PROCEDURE CalculaTotalTransacoes (
 IN item_id INT,
 OUT total_entradas INT,
 OUT total_saidas INT
)
BEGIN
 -- Total de entradas
 SELECT SUM(Quantidade) INTO total_entradas
 FROM Transacao_item
 WHERE transacao_id IN (
 SELECT id
 FROM Transacao
 WHERE tipo = 'Entrada'
 AND data >= DATE_SUB(NOW(), INTERVAL 1 YEAR) -- Considera apenas transações do último ano
 ) AND item_id = item_id;

 -- Total de saídas
 SELECT SUM(Quantidade) INTO total_saidas
 FROM Transacao_item
 WHERE transacao_id IN (
 SELECT id
 FROM Transacao
 WHERE tipo = 'Saída'
 AND data >= DATE_SUB(NOW(), INTERVAL 1 YEAR) -- Considera apenas transações do último ano
 ) AND item_id = item_id;
END //

DELIMITER ;

-- Define variáveis para os resultados
SET @total_entradas = 0;
SET @total_saidas = 0;

-- Chamando a stored procedure 
CALL CalculaTotalTransacoes(1, @total_entradas, @total_saidas);

-- Exibe os resultados
SELECT @total_entradas AS Total_Entradas, @total_saidas AS Total_Saidas;

-- INDEX
CREATE INDEX idx_transacao_item ON Transacao_item(transacao_id, item_id);
--
INSERT INTO Usuario(Nome_usuario, Senha_hashed, Area_acesso, Funcionario_id)VALUES
('joaos', '$2a$12$jXsn80H9a9y6mTD7CNmp7ewon6H2Ph3YaS2oXta2wha4Hva7Sg4ca', 'Gerenciamento', 1),
('mariaf', '$2a$12$q.JH11IBRvIMG0UwfufzI.ST43PEvz0Pghu5vslBB6uk9i1QniPVO', 'Vendas', 2),
('carloss', '$2a$12$GGxHFGNinGjdxdPizDPo3e9UL2/i9hgvuHtXSMVmHbmf99L.dl67O', 'Compras', 3),
('anao', '$2a$12$/ewNFzo4VCxHS.W0y6KSFODlSslCQODUJDL4F5VE6w.pyQgEmzEp6', 'Armazém', 4);