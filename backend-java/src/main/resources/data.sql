CREATE TABLE IF NOT EXISTS clientes
(
    id          SERIAL PRIMARY KEY,
    nome        VARCHAR(100),
    limite      DECIMAL(10, 2),
    saldo_atual DECIMAL(10, 2) DEFAULT 0
);

INSERT INTO clientes (nome, limite)
VALUES ('o barato sai caro', 1000 * 100);
INSERT INTO clientes (nome, limite)
VALUES ('zan corp ltda', 800 * 100);
INSERT INTO clientes (nome, limite)
VALUES ('les cruders', 10000 * 100);
INSERT INTO clientes (nome, limite)
VALUES ('padaria joia de cocaia', 100000 * 100);
INSERT INTO clientes (nome, limite)
VALUES ('kid mais', 5000 * 100);

CREATE TYPE tipo_transacao AS ENUM ('r', 'd');
CREATE TABLE IF NOT EXISTS transacoes
(
    id           SERIAL PRIMARY KEY,
    cliente_id INTEGER REFERENCES clientes(id) NOT NULL,
    tipo         tipo_transacao DEFAULT 'r' NOT NULL,
    descricao    varchar(10)    NOT NULL,
    realizada_em timestamp DEFAULT CURRENT_TIMESTAMP,
    valor        DECIMAL(10, 2) NOT NULL
);