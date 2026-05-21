package com.vinicius.meu_salao_digital_api.busines.controller;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.service.EmpresaService;
import com.vinicius.meu_salao_digital_api.busines.service.UsuarioPreRegistroService;
import com.vinicius.meu_salao_digital_api.busines.service.UsuarioService;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario-pre-registro")
@RequiredArgsConstructor
public class UsuarioPreRegistroController {

    private final UsuarioPreRegistroService usuarioPreRegistroService;


    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvar(@Valid @RequestBody UsuarioPreRegistroCadastroDTO usuario) {
        return usuarioPreRegistroService.salvarUsuario(usuario);
    }

    @GetMapping("/buscar")
    public ResponseEntity<?> buscar(String email){
        return usuarioPreRegistroService.buscarUsuarios(email);
    }

    @DeleteMapping("/excluir")
    public ResponseEntity<?> deletar(@RequestParam Integer id){
        return usuarioPreRegistroService.deletar(id);
    }


}
