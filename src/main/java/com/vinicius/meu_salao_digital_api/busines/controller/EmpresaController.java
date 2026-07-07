package com.vinicius.meu_salao_digital_api.busines.controller;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.service.EmpresaService;
import com.vinicius.meu_salao_digital_api.busines.service.UsuarioService;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/empresa")
@RequiredArgsConstructor
public class EmpresaController {

    private final UsuarioService usuarioService;
    private final EmpresaService empresaService;


    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvarEmpresa(@Valid @RequestBody EmpresaCadastroDTO empresa) {
        return empresaService.salvarEmpresa(empresa);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(Integer empresaId){
        return empresaService.buscarEmpresas(empresaId);
    }

    @DeleteMapping
    public ResponseEntity<?> deletar(@Valid @RequestParam Integer empresaId){
        return empresaService.deletar(empresaId);
    }



}
