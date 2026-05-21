package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.UsuarioPreRegistroCadastroDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.UsuarioPreRegistro;
import com.vinicius.meu_salao_digital_api.busines.repository.UsuarioPreRegistroRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsuarioPreRegistroService {

    private final UsuarioPreRegistroRepository usuarioPreRegistroRepository;

    public ResponseEntity<?> salvarUsuario(@Valid UsuarioPreRegistroCadastroDTO usuarioPreRegistro){

        if(!usuarioPreRegistroRepository.findByEmail(usuarioPreRegistro.getEmail()).isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado!");
        }

        UsuarioPreRegistro usuario = UsuarioPreRegistro.builder()
                .nome(usuarioPreRegistro.getNome())
                .email(usuarioPreRegistro.getEmail())
                .enterpriseId(usuarioPreRegistro.getEnterpriseId())
                .functionEnterprise(usuarioPreRegistro.getFunctionEnterprise())
                .deleted(false)
                .build();

        usuarioPreRegistroRepository.save(usuario);

        return ResponseEntity.ok("Usuario pré-cadastrado com sucesso!");
    }

    public ResponseEntity<?> buscarUsuarios(@Valid String email){

        if(email == null){
            return ResponseEntity.ok(usuarioPreRegistroRepository.findAllByDeleted(false));
        }
        return ResponseEntity.ok(usuarioPreRegistroRepository.findAllByEmail(email));
    }

    public ResponseEntity<?> deletar(@Valid Integer id) {

        if (usuarioPreRegistroRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

        usuarioPreRegistroRepository.deleteById(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

}
