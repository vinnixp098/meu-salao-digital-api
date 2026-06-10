package com.vinicius.meu_salao_digital_api.busines.controller;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.entitys.Servico;
import com.vinicius.meu_salao_digital_api.busines.service.EmpresaService;
import com.vinicius.meu_salao_digital_api.busines.service.ServicoService;
import com.vinicius.meu_salao_digital_api.busines.service.UsuarioService;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/servico")
@RequiredArgsConstructor
public class ServicoController {

    private final UsuarioService usuarioService;
    private final ServicoService servicoService;


    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@Valid @RequestBody ServicoCadastroDTO servico) {
        return servicoService.salvar(servico);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(@Valid @RequestParam Integer empresaId){
        return servicoService.buscarTodos(empresaId);
    }

    @PutMapping("/alterar-status")
    public ResponseEntity<?> alterarStatus(@Valid @RequestParam Integer servicoId){
        return servicoService.alterarStatusAtivo(servicoId);
    }

    @PutMapping("/editar")
    public ResponseEntity<?> alterarStatus(@Valid @RequestBody Servico servico){
        return servicoService.editar(servico);
    }

    @PutMapping("/alterar-status-promocao")
    public ResponseEntity<?> alterarStatusPromocao(Integer servicoId){
        return servicoService.alterarStatusPromocaoAtivo(servicoId);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletar(@Valid @RequestParam Integer servicoId){
        return servicoService.deletar(servicoId);
    }
}
