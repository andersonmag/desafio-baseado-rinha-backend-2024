DO $$
	BEGIN
		-- Criação do ENUM apenas se não existir
		CREATE TYPE tipo_transacao AS ENUM ('r', 'd');
	EXCEPTION
		WHEN duplicate_object THEN null;
	END
	$$;

-- Criação da tabela clientes
CREATE TABLE IF NOT EXISTS clientes (
	id SERIAL PRIMARY KEY,
	nome VARCHAR(100),
	limite DECIMAL(10, 2),
	saldo_atual DECIMAL(10, 2) DEFAULT 0
);

-- Insere apenas se não existir
INSERT INTO clientes (nome, limite)
SELECT 'o barato sai caro', 1000 * 100
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE nome = 'o barato sai caro');

INSERT INTO clientes (nome, limite)
SELECT 'zan corp ltda', 800 * 100
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE nome = 'zan corp ltda');

INSERT INTO clientes (nome, limite)
SELECT 'les cruders', 10000 * 100
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE nome = 'les cruders');

INSERT INTO clientes (nome, limite)
SELECT 'padaria joia de cocaia', 100000 * 100
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE nome = 'padaria joia de cocaia');

INSERT INTO clientes (nome, limite)
SELECT 'kid mais', 5000 * 100
WHERE NOT EXISTS (SELECT 1 FROM clientes WHERE nome = 'kid mais');

-- Criação da tabela transacoes
CREATE TABLE IF NOT EXISTS transacoes (
	id SERIAL PRIMARY KEY,
	tipo tipo_transacao NOT NULL,
	descricao VARCHAR(10) NOT NULL,
	realizada_em TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
	valor DECIMAL(10, 2) NOT NULL
);