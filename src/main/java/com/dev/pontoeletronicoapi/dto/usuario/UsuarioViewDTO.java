package com.dev.pontoeletronicoapi.dto.usuario;

import com.dev.pontoeletronicoapi.model.Role;

public record UsuarioViewDTO(Long id, String nome, String cpf, Role role) {

}