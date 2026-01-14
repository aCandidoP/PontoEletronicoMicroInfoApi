package com.dev.pontoeletronicoapi.config;

import lombok.Builder;

@Builder
public record JWTUserData(
        Long userId,
        String cpf,
        String role,
        String nome
) {}

