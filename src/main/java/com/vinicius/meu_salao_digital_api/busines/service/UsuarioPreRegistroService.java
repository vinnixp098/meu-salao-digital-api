package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.UsuarioPreRegistroCadastroDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.UsuarioPreRegistro;
import com.vinicius.meu_salao_digital_api.busines.repository.UsuarioPreRegistroRepository;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UsuarioPreRegistroService {

    private final UsuarioPreRegistroRepository usuarioPreRegistroRepository;

    public ResponseEntity<?> salvarUsuario(@Valid UsuarioPreRegistroCadastroDTO usuarioPreRegistro){

        List<UsuarioPreRegistro> usuario = usuarioPreRegistroRepository.findAllByEmail(usuarioPreRegistro.getEmail());

        if(!usuario.isEmpty()){
            return ResponseEntity.status(HttpStatus.CONFLICT).body("Email já cadastrado!");
        }

        UsuarioPreRegistro usuarioData = UsuarioPreRegistro.builder()
                .nome(usuarioPreRegistro.getNome())
                .email(usuarioPreRegistro.getEmail())
                .enterpriseId(usuarioPreRegistro.getEnterprise_id())
                .functionEnterprise(usuarioPreRegistro.getFunction_enterprise())
                .deleted(false)
                .build();

        usuarioPreRegistroRepository.save(usuarioData);

        return ResponseEntity.ok("Usuario pré-cadastrado com sucesso!");
    }

    public ResponseEntity<?> buscarUsuarios(@Valid String email){

        if(email == null){
            return ResponseEntity.ok(usuarioPreRegistroRepository.findAllByDeleted(false));
        }

        Optional <UsuarioPreRegistro> usuario = usuarioPreRegistroRepository.findByEmailAndDeleted(email, false);

        if(usuario.isPresent()){
            return ResponseEntity.ok(usuario.get());
        }

       List <UsuarioPreRegistro> usuarios = usuarioPreRegistroRepository.findAll();

        return ResponseEntity.ok(usuarios);

    }

    public ResponseEntity<?> deletar(@Valid Integer id) {

        if (usuarioPreRegistroRepository.findById(id).isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuário não encontrado!");
        }

        usuarioPreRegistroRepository.deleteById(id);
        return ResponseEntity.ok("Usuário deletado com sucesso!");
    }

}
