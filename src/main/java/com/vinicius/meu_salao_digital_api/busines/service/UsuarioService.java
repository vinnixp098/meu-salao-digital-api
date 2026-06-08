package com.vinicius.meu_salao_digital_api.busines.service;

import com.vinicius.meu_salao_digital_api.busines.dto.LoginDTO;
import com.vinicius.meu_salao_digital_api.busines.dto.LoginResponseDTO;
import com.vinicius.meu_salao_digital_api.busines.dto.UsuarioCadastroDTO;
import com.vinicius.meu_salao_digital_api.busines.dto.UsuarioDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.Empresa;
import com.vinicius.meu_salao_digital_api.busines.entitys.PasswordResetToken;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import com.vinicius.meu_salao_digital_api.busines.entitys.UsuarioPreRegistro;
import com.vinicius.meu_salao_digital_api.busines.repository.EmpresaRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.PasswordResetTokenRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.UsuarioPreRegistroRepository;
import com.vinicius.meu_salao_digital_api.busines.repository.UsuarioRepository;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.Random;

@Service
@RequiredArgsConstructor
public class UsuarioService {

    private final UsuarioRepository repository;
    private UsuarioPreRegistroRepository usuarioPreRegistroRepository;
    private final EmpresaRepository empresaRepository;
    private final PasswordResetTokenRepository tokenRepository;
    private final EmailService emailService;
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public ResponseEntity<?> salvarUsuario(@Valid UsuarioCadastroDTO usuarioDTO) {
        if (repository.findByEmail(usuarioDTO.getEmail()).isPresent()) {
            return ResponseEntity.badRequest().body("E-mail já cadastrado!");
        }

        Usuario usuario = Usuario.builder()
                .nome(usuarioDTO.getNome())
                .email(usuarioDTO.getEmail())
                .senha(passwordEncoder.encode(usuarioDTO.getSenha()))
                .perfil(usuarioDTO.getPerfil() != null ? usuarioDTO.getPerfil() : "USER")
                .empresaId(usuarioDTO.getEmpresaId())
                .ativo(true)
                .build();

        repository.save(usuario);
        return ResponseEntity.ok("Usuário cadastrado com sucesso!");
    }

    public ResponseEntity<?> login(LoginDTO loginDTO) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(loginDTO.getEmail());

        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body(
                    new LoginResponseDTO(false, "Usuário não encontrado!", null, null, null, null, null)
            );
        }

        Usuario usuario = usuarioOpt.get();
        Optional<Empresa> dadosEmpresa = empresaRepository.findById(usuario.getEmpresaId());

        if (dadosEmpresa.isEmpty() || dadosEmpresa.get().getDeleted()) {
            return ResponseEntity.status(403).body("Acesso bloqueado! Entre em contato com o suporte.");
        }

        if (!usuario.getAtivo()) {
            return ResponseEntity.status(403).body(
                    new LoginResponseDTO(false, "Usuário inativo! Entre em contato com o suporte.", null, null, null, null, null)
            );
        }

        if (!passwordEncoder.matches(loginDTO.getSenha(), usuario.getSenha())) {
            return ResponseEntity.status(401).body(
                    new LoginResponseDTO(false, "Email ou senha incorretos!", null, null, null, null, null)
            );
        }

        return ResponseEntity.ok(
                new LoginResponseDTO(true, "Login realizado com sucesso!",
                        usuario.getNome(), usuario.getEmail(), usuario.getPerfil(), usuario.getEmpresaId(), dadosEmpresa.get().getDescription())
        );
    }

    public ResponseEntity<?> buscarAtivos(Integer empresaId) {

        if(empresaId != null){
            List<UsuarioDTO> usuarios = repository.findAllByAtivoAndEmpresaId(true, empresaId);
            return ResponseEntity.ok().body(usuarios);
        }

        List<UsuarioDTO> usuarios = repository.findAllByAtivo(true)
                .stream()
                .map(u -> UsuarioDTO.builder()
                        .id(u.getId())
                        .nome(u.getNome())
                        .email(u.getEmail())
                        .perfil(u.getPerfil())
                        .empresaId(u.getEmpresaId())
                        .build())
                .toList();

        return ResponseEntity.ok().body(usuarios);


    }

    public void deletarPorEmail(String email) {
        repository.deleteByEmail(email);
    }

    public ResponseEntity<?> atualizarUsuarioPorEmail(String email, Usuario usuarioAtualizado) {
        Optional<Usuario> usuarioOpt = repository.findByEmail(email);
        if (usuarioOpt.isEmpty()) {
            return ResponseEntity.status(404).body("Usuário não encontrado!");
        }

        Usuario usuario = usuarioOpt.get();

        if (usuarioAtualizado.getNome() != null) usuario.setNome(usuarioAtualizado.getNome());
        if (usuarioAtualizado.getEmail() != null) usuario.setEmail(usuarioAtualizado.getEmail());
        if (usuarioAtualizado.getSenha() != null)
            usuario.setSenha(passwordEncoder.encode(usuarioAtualizado.getSenha()));

        repository.save(usuario);
        return ResponseEntity.ok("Usuário atualizado com sucesso!");
    }

    private String gerarCodigo() {
        return String.format("%06d", new Random().nextInt(999999));
    }


    @Transactional
    public ResponseEntity<?> enviarCodigoRecuperacao(String email) {

        Optional<Usuario> usuarioOpt =
                repository.findByEmail(email);

        Optional<UsuarioPreRegistro> preRegistroOpt =
                usuarioPreRegistroRepository.findByEmail(email);

        if (usuarioOpt.isEmpty() && preRegistroOpt.isEmpty()) {
            return ResponseEntity
                    .status(404)
                    .body("Usuário não encontrado!");
        }

        Integer usuarioId;
        String emailUsuario;
        String nomeUsuario;

        if (usuarioOpt.isPresent()) {
            Usuario usuario = usuarioOpt.get();

            usuarioId = usuario.getId();
            emailUsuario = usuario.getEmail();
            nomeUsuario = usuario.getNome();
        } else {
            UsuarioPreRegistro usuario = preRegistroOpt.get();

            usuarioId = usuario.getId();
            emailUsuario = usuario.getEmail();
            nomeUsuario = usuario.getNome();
        }

        String codigo = gerarCodigo();

        tokenRepository.deleteAllByUsuarioId(usuarioId);

        PasswordResetToken token = PasswordResetToken.builder()
                .usuarioId(usuarioId)
                .email(emailUsuario)
                .token(codigo)
                .expiration(LocalDateTime.now().plusMinutes(10))
                .build();

        tokenRepository.save(token);

        emailService.enviarCodigo(
                emailUsuario,
                codigo,
                nomeUsuario
        );

        return ResponseEntity.ok(
                "Código de validação enviado para o e-mail!"
        );
    }


    public ResponseEntity<?> validarCodigoRecuperacao(String email, String codigo) {
        PasswordResetToken token = tokenRepository.findByEmailAndToken(email, codigo)
                .orElse(null);

        if (token == null) {
            return ResponseEntity.status(400).body("Código inválido!");
        }

        if (token.getExpiration().isBefore(LocalDateTime.now())) {
            tokenRepository.delete(token);
            return ResponseEntity.status(400).body("Código expirado!");
        }

        return ResponseEntity.ok("Código validado com sucesso!");



    }
}

