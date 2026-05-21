package com.vinicius.meu_salao_digital_api.busines.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;

@Getter
public class UsuarioPreRegistroCadastroDTO {

    public String nome;
    public String email;
    @JsonProperty("enterprise_id")
    public Integer enterpriseId;
    @JsonProperty("function_enterprise")
    public String functionEnterprise;
}
