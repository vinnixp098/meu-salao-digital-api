package com.vinicius.meu_salao_digital_api.busines.controller;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.service.AtendimentoService;
import com.vinicius.meu_salao_digital_api.busines.service.EmpresaService;
import com.vinicius.meu_salao_digital_api.busines.service.ServicoService;
import com.vinicius.meu_salao_digital_api.busines.service.UsuarioService;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/atendimento")
@RequiredArgsConstructor
public class AtendimentoController {

    private final AtendimentoService atendimentoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@Valid @RequestBody AtendimentoCadastroDTO atendimento) {
        return atendimentoService.salvar(atendimento);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(
            @Valid @RequestParam Integer empresaId,
            @Valid @RequestParam StatusAtendimento status,
            LocalDate dataInicio,
            LocalDate dataFim
    ){
        return atendimentoService.buscarTodos(empresaId, status, dataInicio, dataFim);
    }

    @PutMapping("/alterar-status")
    public ResponseEntity<?> alterarStatus(@Valid @RequestParam Integer atendimentoId, @Valid @RequestParam StatusAtendimento status){
        return atendimentoService.alterarStatusAtivo(atendimentoId, status);
    }
}
