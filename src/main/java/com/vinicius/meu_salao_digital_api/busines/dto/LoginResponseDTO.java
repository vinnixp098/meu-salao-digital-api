package com.vinicius.meu_salao_digital_api.busines.dto;

public record LoginResponseDTO(
        boolean logado,
        String mensagem,
        String usuario,
        String email,
        String perfil,
        Integer empresaId,
        String empresaName
) {}
