package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.dto.StatusAtendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND a.cliente = :cliente
        AND a.status = :status
        AND (a.deletado IS NULL OR a.deletado = false)
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
        AND a.dataCriacao >= :dataInicio
        AND a.dataCriacao < :dataFim
        AND (a.deletado IS NULL OR a.deletado = false)
        ORDER BY a.dataCriacao DESC
    """)
    List<Atendimento> buscarTodosPorPeriodo(
            @Param("empresaId") Integer empresaId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND a.status = :status
        AND a.dataCriacao >= :dataInicio
        AND a.dataCriacao < :dataFim
        AND (a.deletado IS NULL OR a.deletado = false)
        ORDER BY a.dataCriacao DESC
    """)
    List<Atendimento> buscarTodosPorStatusEPeriodo(
            @Param("empresaId") Integer empresaId,
            @Param("status") StatusAtendimento status,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND a.status = :status
        AND (a.deletado IS NULL OR a.deletado = false)
        ORDER BY a.dataCriacao DESC
    """)
    List<Atendimento> buscarTodosPorEmpresaEStatus(
            @Param("empresaId") Integer empresaId,
            @Param("status") StatusAtendimento status
    );

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND (a.deletado IS NULL OR a.deletado = false)
        ORDER BY a.dataCriacao DESC
    """)
    List<Atendimento> buscarTodosPorEmpresa(
            @Param("empresaId") Integer empresaId
    );

    @Query("""
        SELECT a
        FROM Atendimento a
        WHERE a.empresaId = :empresaId
        AND (a.deletado IS NULL OR a.deletado = false)
        AND a.status NOT IN ('CANCELADO', 'AGENDADO')
        AND a.dataCriacao >= :dataInicio
        AND a.dataCriacao < :dataFim
        ORDER BY a.dataCriacao DESC
    """)
    List<Atendimento> buscarAtendimentos(
            @Param("empresaId") Integer empresaId,
            @Param("dataInicio") LocalDateTime dataInicio,
            @Param("dataFim") LocalDateTime dataFim
    );



}