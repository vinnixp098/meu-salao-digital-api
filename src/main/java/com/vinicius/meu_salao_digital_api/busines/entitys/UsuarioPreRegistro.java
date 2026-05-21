package com.vinicius.meu_salao_digital_api.busines.entitys;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_pre_registration")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UsuarioPreRegistro {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    @Column(name = "enterprise_id")
    private Integer enterpriseId;

    @Column(name = "function_enterprise")
    private String functionEnterprise;

    public Boolean deleted;

    @CreationTimestamp
    @Column(name = "created_at")
    private LocalDateTime createdAt;

}
