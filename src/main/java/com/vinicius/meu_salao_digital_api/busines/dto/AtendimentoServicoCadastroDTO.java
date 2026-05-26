package com.vinicius.meu_salao_digital_api.busines.dto;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@Getter
public class AtendimentoServicoCadastroDTO {

    private Integer empresaId;

    private Integer atendimentoId;

    private Integer servicoId;

    private Integer usuarioId;

    private String servicoDescricao;

    private String usuarioNome;

    private BigDecimal valorTotal;
}