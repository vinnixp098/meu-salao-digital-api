package com.vinicius.meu_salao_digital_api.busines.entitys;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Entity
@Table(name = "atendimento_servico")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AtendimentoServico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "atendimento_id")
    private Integer atendimentoId;

    @Column(name = "empresa_id")
    private Integer empresaId;

    @Column(name = "servico_id")
    private Integer servicoId;

    @Column(name = "usuario_id")
    private Integer usuarioId;

    @Column(name = "servico_descricao")
    private String servicoDescricao;

    @Column(name = "usuario_nome")
    private String usuarioNome;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private Boolean deletado;
}