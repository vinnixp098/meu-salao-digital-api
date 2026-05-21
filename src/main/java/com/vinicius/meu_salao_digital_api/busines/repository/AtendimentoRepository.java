package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.dto.StatusAtendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface AtendimentoRepository extends JpaRepository<Atendimento, Integer> {

    List<Atendimento> findAllByEmpresaIdAndClienteAndStatus(Integer empresaId, String cliente, StatusAtendimento statusAtendimento);

    List<Atendimento> findAllByEmpresaIdBetWeenDates(Integer empresa, LocalDate dataInicio, LocalDate dataFim);

    List<Atendimento> findAllByEmpresaIdAndStatusBetWeenDates(Integer empresa, StatusAtendimento status, LocalDate dataInicio, LocalDate dataFim);
}
