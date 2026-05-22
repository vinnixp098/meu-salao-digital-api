package com.vinicius.meu_salao_digital_api.busines.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import lombok.Getter;

@Getter
public class UsuarioPreRegistroCadastroDTO {

    public String nome;
    public String email;
    public Integer enterprise_id;
    public String function_enterprise;
}
