package com.dev.pontoeletronicoapi.dto.usuario;

import jakarta.validation.constraints.NotEmpty;

public record UsuarioCreateDTO(@NotEmpty(message = "Nome é obrigatório") String nome,
                               @NotEmpty(message = "CPF é obrigatório") String cpf,
                               @NotEmpty(message = "Senha é obrigatória") String senha) {
}
