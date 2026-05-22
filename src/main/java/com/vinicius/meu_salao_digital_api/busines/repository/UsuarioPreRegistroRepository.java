package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.entitys.UsuarioPreRegistro;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
@Repository
public interface UsuarioPreRegistroRepository extends JpaRepository<UsuarioPreRegistro, Integer> {
    Optional <UsuarioPreRegistro> findByEmail(@Valid String email);

    @Transactional
    void deleteById(Integer id);

    List<UsuarioPreRegistro> findAllByDeleted(Boolean deleted);

    List<UsuarioPreRegistro> findAllByEmail(@Valid String email);


    Optional<UsuarioPreRegistro> findByEmailAndDeleted(@Valid String email, Boolean deleted);
}
