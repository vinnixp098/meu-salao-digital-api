package com.vinicius.meu_salao_digital_api.busines.entitys;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "enterprises", schema = "public")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Empresa {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    private String description;

    private String email;

    private String telephone;

    @Column(unique = true, nullable = false)
    private String document;

    @CreationTimestamp
    private LocalDateTime created_at;

    private Boolean deleted;
}
