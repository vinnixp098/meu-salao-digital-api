package com.vinicius.meu_salao_digital_api.busines.entitys;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "servicos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Servico {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String descricao;

    private BigDecimal valor;

    private BigDecimal valor_promocao;

    @Column(name = "empresa_id")
    private Integer empresaId;

    private Boolean promocao_ativo;

    private Boolean ativo;
}