package com.vinicius.meu_salao_digital_api.busines.entitys;

import com.vinicius.meu_salao_digital_api.busines.dto.StatusAtendimento;
import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "atendimentos")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Atendimento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String cliente;

    @Column(name = "empresa_id")
    private Integer empresaId;

    @Column(name = "data_agendamento")
    private LocalDateTime dataAgendamento;

    @CreationTimestamp
    @Column(name = "data_criacao")
    private LocalDateTime dataCriacao;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private StatusAtendimento status;

    @Column(name = "valor_total")
    private BigDecimal valorTotal;

    private Boolean deletado;
}