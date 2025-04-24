-- Inserir clientes de teste
INSERT INTO clientes (id, nome, email, telefone, data_cadastro) 
VALUES ('123e4567-e89b-12d3-a456-426614174000', 'João Silva', 'joao@email.com', '11999999999', CURRENT_TIMESTAMP);

INSERT INTO clientes (id, nome, email, telefone, data_cadastro)
VALUES ('223e4567-e89b-12d3-a456-426614174000', 'Maria Santos', 'maria@email.com', '11988888888', CURRENT_TIMESTAMP);

-- Inserir endereços de teste
INSERT INTO endereco (id, cliente_id, rua, numero, bairro, cidade, estado, cep)
VALUES (1, '123e4567-e89b-12d3-a456-426614174000', 'Rua das Flores', '123', 'Centro', 'São Paulo', 'SP', '01234-567');

INSERT INTO endereco (id, cliente_id, rua, numero, bairro, cidade, estado, cep)
VALUES (2, '223e4567-e89b-12d3-a456-426614174000', 'Av. Principal', '456', 'Jardins', 'São Paulo', 'SP', '04567-890');

-- Inserir ordens de serviço de teste
INSERT INTO ordens_servico (id, cliente_id, descricao, data_abertura)
VALUES (1, '123e4567-e89b-12d3-a456-426614174000', 'Manutenção de Computador', CURRENT_TIMESTAMP);

INSERT INTO ordens_servico (id, cliente_id, descricao, data_abertura)
VALUES (2, '223e4567-e89b-12d3-a456-426614174000', 'Instalação de Software', CURRENT_TIMESTAMP); 