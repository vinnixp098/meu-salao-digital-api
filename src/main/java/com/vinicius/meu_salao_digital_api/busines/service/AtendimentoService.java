package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.AtendimentoServico;
import com.vinicius.meu_salao_digital_api.busines.repository.AtendimentoRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.AtendimentoServicoRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.EmpresaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;
    private final AtendimentoServicoRepository atendimentoServicoRepository;
    private final EmpresaRepository empresaRepository;

    public ResponseEntity<?> salvar(@Valid AtendimentoCadastroDTO atendimento){

        if(empresaRepository.findById(atendimento.getEmpresaId()).isEmpty()){
            return ResponseEntity.badRequest().body("Empresa não encontrada!");
        }

        if(atendimentoRepository.buscarAtendimentoEmAndamento(
                atendimento.getEmpresaId(),
                atendimento.getCliente(),
                StatusAtendimento.EM_ANDAMENTO
        ).size() > 0){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("O cliente já possui um atendimento em andamento!");
        }

        Atendimento atendimentoData = Atendimento.builder()
                .cliente(atendimento.getCliente())
                .status(atendimento.getStatus())
                .valorTotal(atendimento.getValor_total())
                .empresaId(atendimento.getEmpresaId())
                .dataAgendamento(atendimento.getData_agendamento())
                .build();

        Atendimento salvo = atendimentoRepository.save(atendimentoData);

        return ResponseEntity.ok(salvo.getId());

    }

    public ResponseEntity<?> buscarTodos(
            Integer empresa,
            StatusAtendimento status,
            String dataInicio,
            String dataFim
    ){

        if(dataInicio == null && dataFim == null && status == null){

            List<Atendimento> atendimentos = atendimentoRepository.buscarTodosPorEmpresa(empresa);

            List<AtendimentoResponseDTO> atendimentoData = atendimentos
                    .stream()
                    .map(u -> AtendimentoResponseDTO.builder()
                            .id(u.getId())
                            .cliente(u.getCliente())
                            .status(u.getStatus())
                            .valorTotal(atendimentoServicoRepository.buscarTodosPorEmpresaEAtendimentoId(u.getEmpresaId(), u.getId()).stream().map(AtendimentoServico::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add))
                            .empresaId(u.getEmpresaId())
                            .dataCriacao(u.getDataCriacao())
                            .dataAgendamento(u.getDataAgendamento())
                            .servicos(atendimentoServicoRepository.buscarTodosPorEmpresaEAtendimentoId(u.getEmpresaId(), u.getId()))
                            .build())
                    .toList();



            return ResponseEntity.ok(atendimentoData);
        }

        if(dataInicio == null && dataFim == null && status != null){
            List<Atendimento> atendimentos = atendimentoRepository.buscarTodosPorEmpresaEStatus(empresa, status);

            List<AtendimentoResponseDTO> atendimentoData = atendimentos
                    .stream()
                    .map(u -> AtendimentoResponseDTO.builder()
                            .id(u.getId())
                            .cliente(u.getCliente())
                            .status(u.getStatus())
                            .valorTotal(atendimentoServicoRepository.buscarTodosPorEmpresaEAtendimentoId(u.getEmpresaId(), u.getId()).stream().map(AtendimentoServico::getValorTotal).reduce(BigDecimal.ZERO, BigDecimal::add))
                            .empresaId(u.getEmpresaId())
                            .dataCriacao(u.getDataCriacao())
                            .dataAgendamento(u.getDataAgendamento())
                            .servicos(atendimentoServicoRepository.buscarTodosPorEmpresaEAtendimentoId(u.getEmpresaId(), u.getId()))
                            .build())
                    .toList();



            return ResponseEntity.ok(atendimentoData);
        }

        LocalDateTime inicio = LocalDate.parse(dataInicio).atStartOfDay();
        LocalDateTime fim = LocalDate.parse(dataFim).atTime(23, 59, 59); // 23:59:59

        if(dataInicio != null && dataFim != null && status == null){
            return ResponseEntity.ok(
                    atendimentoRepository.buscarTodosPorPeriodo(
                            empresa,
                            inicio,
                            fim
                    )
            );
        }

        return ResponseEntity.ok(
                atendimentoRepository.buscarTodosPorStatusEPeriodo(
                        empresa,
                        status,
                        inicio,
                        fim
                )
        );
    }

    public ResponseEntity<?> alterarStatusAtivo(Integer id, StatusAtendimento status){

        if (StatusAtendimento.CANCELADO == status){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Não é possível alterar o status de um atendimento cancelado!");
        }

        Atendimento atendimento = atendimentoRepository.findById(id).get();
        atendimento.setStatus(status);
        atendimentoRepository.save(atendimento);
        return ResponseEntity.ok("Status do atendimento movido para " + status.toString() + "com sucesso!");
    }


    public ResponseEntity<?> buscarTotalizador(@Valid Integer empresaId, TipoFiltroTempo tipoTempo) {
        LocalDate hoje = LocalDate.parse(LocalDate.now().toString());
        LocalDate seteDiasAtras = hoje.minusDays(7);
        LocalDate umMesAtras = hoje.minusDays(30);

        if (tipoTempo == TipoFiltroTempo.DIA) {
            List<Atendimento> atendimentos = atendimentoRepository.buscarAtendimentos(empresaId, hoje.atStartOfDay(), hoje.atTime(23, 59,59));
            ResponseTotalizadoresDTO totalizador = calcularTotalizadores(atendimentos);
            return ResponseEntity.ok(totalizador);
        }

        if (tipoTempo == TipoFiltroTempo.SEMANA) {
            List<Atendimento> atendimentos = atendimentoRepository.buscarAtendimentos(empresaId, seteDiasAtras.atStartOfDay(), hoje.atTime(23, 59,59));
            ResponseTotalizadoresDTO totalizador = calcularTotalizadores(atendimentos);
            return ResponseEntity.ok(totalizador);
        }

        if (tipoTempo == TipoFiltroTempo.MES) {
            List<Atendimento> atendimentos = atendimentoRepository.buscarAtendimentos(empresaId, umMesAtras.atStartOfDay(), hoje.atTime(23, 59,59));
            ResponseTotalizadoresDTO totalizador = calcularTotalizadores(atendimentos);
            return ResponseEntity.ok(totalizador);

        }

        return ResponseEntity.badRequest().body("Não foi possível buscar os últimos atendimentos!");


    }

    public ResponseTotalizadoresDTO calcularTotalizadores(List<Atendimento> atendimentos){
        ResponseTotalizadoresDTO response = new ResponseTotalizadoresDTO();
        response.setAtendimentos(atendimentos.size());
        response.setEmAndamento(atendimentos.stream().filter(
                atendimento -> atendimento.getStatus() == StatusAtendimento.EM_ANDAMENTO
        ).toList().size());

        List<Atendimento> atendimentosFinalizados = atendimentos.stream().filter(atendimento -> atendimento.getStatus() == StatusAtendimento.FINALIZADO).toList();

        response.setFinalizados(atendimentosFinalizados.size());

        response.setFaturamento(

                atendimentosFinalizados.stream()
                        .map(Atendimento::getValorTotal)
                        .reduce(BigDecimal.ZERO, BigDecimal::add)
        );

        return response;

    }
}
