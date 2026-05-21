package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.dto.UsuarioDTO;
import com.vinicius.meu_salao_digital_api.busines.entitys.Usuario;
import com.vinicius.meu_salao_digital_api.busines.entitys.UsuarioPreRegistro;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UsuarioPreRegistroRepository extends JpaRepository<UsuarioPreRegistro, Integer> {
    List<UsuarioPreRegistro> findByEmail(String email);

    @Transactional
    void deleteById(Integer id);

    List<UsuarioPreRegistro> findAllByDeleted(Boolean deleted);

    List<UsuarioPreRegistro> findAllByEmail(@Valid String email);
}
