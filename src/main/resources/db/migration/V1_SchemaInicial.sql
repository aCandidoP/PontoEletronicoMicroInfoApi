CREATE TABLE usuarios(
    id serial PRIMARY KEY,
    CPF VARCHAR(11),
    nome varchar(255),
    senha varchar(255)
)

CREATE TABLE ponto_batida(
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    tipo VARCHAR(20) NOT NULL, -- ENTRADA / SAIDA
    observacao TEXT,

    CONSTRAINT fk_usuario
      FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);
