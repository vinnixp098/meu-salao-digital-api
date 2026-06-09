package com.vinicius.meu_salao_digital_api.busines.controller;

import com.vinicius.meu_salao_digital_api.busines.dto.*;
import com.vinicius.meu_salao_digital_api.busines.service.UsuarioService;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/usuario")
@RequiredArgsConstructor
public class UsuarioController {

    private final UsuarioService usuarioService;

    @PostMapping("/cadastrar")
    public ResponseEntity<?> salvarUsuario(@Valid @RequestBody UsuarioCadastroDTO usuario) {
        return usuarioService.salvarUsuario(usuario);
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@Valid @RequestBody LoginDTO login) {
        return usuarioService.login(login);
    }

    @GetMapping("/buscar-todos")
    public ResponseEntity<?> buscarTodos(Integer empresaId){
        return usuarioService.buscarAtivos(empresaId);
    }

    @DeleteMapping("/deletar")
    public ResponseEntity<?> deletarUsuarioPorEmail(@RequestParam String email) {
        usuarioService.deletarPorEmail(email);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

    @PutMapping("/atualizar")
    public ResponseEntity<?> atualizarUsuarioPorEmail(@RequestParam String email, @RequestBody Usuario usuario) {
        return usuarioService.atualizarUsuarioPorEmail(email, usuario);
    }

    @PutMapping("/alterar-senha")
    public ResponseEntity<?> alterarSenha(@RequestBody AlterarSenhaDTO usuario) {
        return usuarioService.alterarSenha(usuario.getEmail(), usuario.getSenha());
    }

    @PostMapping("/enviar-codigo")
    public ResponseEntity<?> enviarCodigo(@RequestBody PasswordRecoveryRequestDTO dto) {
        return usuarioService.enviarCodigoRecuperacao(dto.getEmail());
    }

    @PostMapping("/validar-codigo")
    public ResponseEntity<?> validarCodigo(@RequestBody PasswordCodeValidationDTO dto) {
        return usuarioService.validarCodigoRecuperacao(dto.getEmail(), dto.getCodigo());
    }
}
