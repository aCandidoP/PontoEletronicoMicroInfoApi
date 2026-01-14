CREATE TABLE usuarios(
    id serial PRIMARY KEY,
    CPF VARCHAR(11) NOT NULL UNIQUE,
    nome varchar(255) NOT NULL ,
    senha varchar(255) NOT NULL ,
    role varchar(20) NOT NULL DEFAULT 'USER',
    CONSTRAINT chk_role
        CHECK (role IN ('USER', 'ADMIN'))
);

CREATE TABLE ponto_batida(
    id BIGSERIAL PRIMARY KEY,
    usuario_id BIGINT NOT NULL,
    data_hora TIMESTAMP NOT NULL,
    tipo VARCHAR(20) NOT NULL, -- ENTRADA / SAIDA
    observacao TEXT,

    CONSTRAINT chk_tipo_ponto
        CHECK (tipo IN ('ENTRADA', 'SAIDA')),

    CONSTRAINT fk_usuario
      FOREIGN KEY (usuario_id) REFERENCES usuarios(id)
);

INSERT INTO usuarios(
     CPF, nome, senha, role
) VALUES ('00000000000', 'Administrador', '$2a$10$N7HerE/CxExqbEIqpSfu5u7ybFQx6aNcl2n5Sjud04meJojFW1Oqq', 'ADMIN');
