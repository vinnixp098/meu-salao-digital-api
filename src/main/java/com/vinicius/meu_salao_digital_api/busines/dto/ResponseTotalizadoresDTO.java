package com.vinicius.meu_salao_digital_api.busines.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class ResponseTotalizadoresDTO {
    private Integer atendimentos;
    private Integer emAndamento;
    private Integer finalizados;
    private BigDecimal faturamento;
}
