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

import javax.swing.text.html.Option;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
public class AtendimentoServicoService {
    private final AtendimentoServicoRepository atendimentoServicoRepository;
    private final EmpresaRepository empresaRepository;

    public ResponseEntity<?> salvar(@Valid AtendimentoServicoCadastroDTO atendimento){

        if(empresaRepository.findById(atendimento.getEmpresaId()).isEmpty()){
            return ResponseEntity.badRequest().body("Empresa não encontrada!");
        }

        if(atendimentoServicoRepository.buscarAtendimentoEmAndamento(
                atendimento.getEmpresaId(),
                atendimento.getAtendimentoId(),
                atendimento.getServicoId()

        ).size() > 0){
            return ResponseEntity.status(HttpStatus.CONFLICT)
                    .body("Já existe esse serviço no atendimento!");
        }

        AtendimentoServico atendimentoData = AtendimentoServico.builder()
                .empresaId(atendimento.getEmpresaId())
                .atendimentoId(atendimento.getAtendimentoId())
                .servicoId(atendimento.getServicoId())
                .usuarioId(atendimento.getUsuarioId())
                .servicoDescricao(atendimento.getServicoDescricao())
                .usuarioNome(atendimento.getUsuarioNome())
                .valorTotal(atendimento.getValorTotal())
                .deletado(false)
                .build();

        atendimentoServicoRepository.save(atendimentoData);
        return ResponseEntity.ok("Serviço atribuído ao atendimento com sucesso!");

    }

    public ResponseEntity<?> buscarTodos(
            Integer empresa,
            Integer atendimentoId
    ){

      List<AtendimentoServico> atendimentoServicos = atendimentoServicoRepository.buscarTodosPorEmpresaEAtendimentoId(empresa, atendimentoId );

      return ResponseEntity.ok().body(atendimentoServicos);

    }

    public ResponseEntity<?> deletar(@Valid Integer id) {

        Optional<AtendimentoServico> atendimentoServico = atendimentoServicoRepository.findById(id);

        if(atendimentoServico.get() == null){
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Serviço não encontrado");
        }

        atendimentoServico.get().setDeletado(true);
        atendimentoServicoRepository.save(atendimentoServico.get());
        return ResponseEntity.ok("Serviço deletado com sucesso!");
    }


}
