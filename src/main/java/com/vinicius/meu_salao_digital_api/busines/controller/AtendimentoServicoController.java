package com.vinicius.meu_salao_digital_api.busines.controller;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.service.*;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

@RestController
@RequestMapping("api/v1/atendimento-servico")
@RequiredArgsConstructor
public class AtendimentoServicoController {

    private final AtendimentoServicoService atendimentoServicoService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@Valid @RequestBody AtendimentoServicoCadastroDTO atendimento) {
        return atendimentoServicoService.salvar(atendimento);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(
            @Valid @RequestParam Integer empresaId,
            @Valid @RequestParam Integer atendimentoId
    ){
        return atendimentoServicoService.buscarTodos(empresaId, atendimentoId);
    }

    @PutMapping
    public ResponseEntity<?> deletar(@Valid @RequestParam Integer id){

        return atendimentoServicoService.deletar(id);
    }




}
