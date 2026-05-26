package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.dto.StatusAtendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.AtendimentoServico;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.List;

public interface AtendimentoServicoRepository extends JpaRepository<AtendimentoServico, Integer> {


    @Query("""
        SELECT a
        FROM AtendimentoServico a
        WHERE a.empresaId = :empresaId
        AND a.atendimentoId = :atendimentoId
        AND a.servicoId = :servicoId
        AND (a.deletado IS NULL OR a.deletado = false)
    """)
    List<AtendimentoServico> buscarAtendimentoEmAndamento(
            @Param("empresaId") Integer empresaId,
            @Param("atendimentoId") Integer atendimentoId,
            @Param("servicoId") Integer servicoId
    );

    @Query("""
        SELECT a
        FROM AtendimentoServico a
        WHERE a.empresaId = :empresaId
        AND a.atendimentoId = :atendimentoId
        AND (a.deletado IS NULL OR a.deletado = false)
    """)
    List<AtendimentoServico> buscarTodosPorEmpresaEAtendimentoId(
            @Param("empresaId") Integer empresaId,
            @Param("atendimentoId") Integer atendimentoId
    );



}