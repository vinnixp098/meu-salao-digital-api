package com.vinicius.meu_salao_digital_api.busines.repository;

import com.vinicius.meu_salao_digital_api.busines.entitys.PasswordResetToken;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Long> {
    Optional<PasswordResetToken> findByEmailAndToken(String email, String token);
    void deleteByEmail(String email);

    void deleteAllByUsuarioId(Integer id);
}
