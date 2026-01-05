package com.dev.pontoeletronicoapi.dto.login;

import jakarta.validation.constraints.NotEmpty;

public record LoginRequest(@NotEmpty(message = "O CPF é obrigatório") String cpf,
                           @NotEmpty(message = "Senha é obrigatória") String senha) {
}
