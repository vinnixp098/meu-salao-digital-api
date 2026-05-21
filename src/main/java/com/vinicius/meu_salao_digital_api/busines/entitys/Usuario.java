package com.vinicius.meu_salao_digital_api.busines.entitys;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "usuarios")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String nome;

    @Column(unique = true, nullable = false)
    private String email;

    private String senha;

    @CreationTimestamp
    private LocalDateTime dataCriacao;

    private String perfil;

    @Column(name = "empresa_id")
    private Integer empresaId;

    private Boolean ativo;
}
