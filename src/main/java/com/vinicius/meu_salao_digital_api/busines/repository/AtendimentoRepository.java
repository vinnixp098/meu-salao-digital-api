package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.dto.StatusAtendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND a.cliente = :cliente
        AND a.status = :status
    """)
    List<Atendimento> buscarAtendimentoEmAndamento(
            @Param("empresaId") Integer empresaId,
            @Param("cliente") String cliente,
            @Param("status") StatusAtendimento status
    );

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND DATE(a.data_agendamento) BETWEEN :dataInicio AND :dataFim
        ORDER BY a.data_agendamento DESC
    """)
    List<Atendimento> buscarTodosPorPeriodo(
            @Param("empresaId") Integer empresaId,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND a.status = :status
        AND DATE(a.data_agendamento) BETWEEN :dataInicio AND :dataFim
        ORDER BY a.data_agendamento DESC
    """)
    List<Atendimento> buscarTodosPorStatusEPeriodo(
            @Param("empresaId") Integer empresaId,
            @Param("status") StatusAtendimento status,
            @Param("dataInicio") LocalDate dataInicio,
            @Param("dataFim") LocalDate dataFim
    );
}
