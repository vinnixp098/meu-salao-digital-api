package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.dto.UsuarioDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    Optional<Usuario> findByEmail(String email);

    @Transactional
    void deleteByEmail(String email);

    List<Usuario> findAllByAtivo(Boolean ativo);

    List<UsuarioDTO> findAllByAtivoAndEmpresaId(boolean b, Integer empresaId);
}
