-- Criação da tabela de clientes
CREATE TABLE clientes (
    id UUID PRIMARY KEY,
    nome VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL,
    telefone VARCHAR(20),
    data_cadastro TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Criação da tabela de endereços
CREATE TABLE enderecos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id UUID NOT NULL,
    rua VARCHAR(100) NOT NULL,
    numero VARCHAR(10) NOT NULL,
    complemento VARCHAR(50),
    bairro VARCHAR(50),
    cidade VARCHAR(50) NOT NULL,
    estado VARCHAR(2) NOT NULL,
    cep VARCHAR(10),
    CONSTRAINT fk_cliente_endereco FOREIGN KEY (cliente_id) REFERENCES clientes(id)
);

-- Criação da tabela de ordens de serviços
CREATE TABLE ordens_servicos (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    cliente_id UUID NOT NULL,
    data_abertura TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    data_fechamento TIMESTAMP,
    descricao TEXT NOT NULL,
    status VARCHAR(20) DEFAULT 'Aberto',
    CONSTRAINT fk_cliente_ordem FOREIGN KEY (cliente_id) REFERENCES clientes(id)
); 