package com.vinicius.meu_salao_digital_api.busines.dto;
import com.vinicius.meu_salao_digital_api.busines.entitys.AtendimentoServico;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@Builder
@Getter
public class AtendimentoResponseDTO {

    private Integer id;

    private String cliente;

    private String telefone;

    private Integer empresaId;

    private LocalDateTime dataCriacao;

    private LocalDateTime dataAgendamento;

    private StatusAtendimento status;

    private BigDecimal valorTotal;

    private List<AtendimentoServico> servicos;

}