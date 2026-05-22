package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.AtendimentoCadastroDTO;
import com.vinicius.meu_salao_digital_api.busines.dto.StatusAtendimento;
import com.vinicius.meu_salao_digital_api.busines.entitys.Atendimento;
import com.vinicius.meu_salao_digital_api.busines.repository.AtendimentoRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.EmpresaRepository;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AtendimentoService {
    private final AtendimentoRepository atendimentoRepository;
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
                .valor_total(atendimento.getValor_total())
                .empresaId(atendimento.getEmpresaId())
                .data_agendamento(atendimento.getData_agendamento())
                .build();

        atendimentoRepository.save(atendimentoData);
        return ResponseEntity.ok("Atendimento cadastrado com sucesso!");

    }

    public ResponseEntity<?> buscarTodos(
            Integer empresa,
            StatusAtendimento status,
            LocalDate dataInicio,
            LocalDate dataFim
    ){

        if(status == null){
            return ResponseEntity.ok(
                    atendimentoRepository.buscarTodosPorPeriodo(
                            empresa,
                            dataInicio,
                            dataFim
                    )
            );
        }

        return ResponseEntity.ok(
                atendimentoRepository.buscarTodosPorStatusEPeriodo(
                        empresa,
                        status,
                        dataInicio,
                        dataFim
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



}
