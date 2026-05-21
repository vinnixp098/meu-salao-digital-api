package com.vinicius.meu_salao_digital_api.busines.dto;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
public class AtendimentoCadastroDTO {


    private String cliente;

    @Column(name = "empresa_id")
    private Integer empresaId;

    private LocalDateTime data_agendamento;

    private StatusAtendimento status;

    private BigDecimal valor_total;

}